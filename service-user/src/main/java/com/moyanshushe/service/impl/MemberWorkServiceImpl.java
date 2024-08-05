package com.moyanshushe.service.impl;

import com.moyanshushe.constant.ItemConstant;
import com.moyanshushe.exception.UnexpectedException;
import com.moyanshushe.exception.work.ConfirmCodeInvaildException;
import com.moyanshushe.exception.work.ItemStatusException;
import com.moyanshushe.model.dto.item.ItemForConfirm;
import com.moyanshushe.model.dto.item.ItemUpdateStatus;
import com.moyanshushe.model.dto.item_code.ItemCodeSubstance;
import com.moyanshushe.model.dto.member_confirm.MemberConfirmSubstanceInput;
import com.moyanshushe.model.entity.*;
import com.moyanshushe.properties.PurchaseProperty;
import com.moyanshushe.service.MemberWorkService;
import com.moyanshushe.utils.ItemCodeGenerator;
import com.moyanshushe.utils.MailUtil;
import com.moyanshushe.utils.RandomUtil;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/*
 * Author: Napbad
 * Version: 1.0
 */


@Service
public class MemberWorkServiceImpl implements MemberWorkService {

    private final MailUtil mailUtil;
    private final JSqlClient jsqlClient;
    private final ItemTable itemTable;
    private final MemberTable memberTable;
    private final MemberConfirmTable memberConfirmTable;
    private final PurchaseProperty memberWorkProperty;
    private final OrderTable orderTable;
    private final UserTable userTable;

    public MemberWorkServiceImpl(
            MailUtil mailUtil,
            JSqlClient jsqlClient,
            PurchaseProperty memberWorkProperty) {

        this.mailUtil = mailUtil;
        this.jsqlClient = jsqlClient;
        this.memberWorkProperty = memberWorkProperty;
        this.itemTable = ItemTable.$;
        this.memberTable = MemberTable.$;
        this.memberConfirmTable = MemberConfirmTable.$;
        this.orderTable = OrderTable.$;
        this.userTable = UserTable.$;
    }

    /**
     * 提醒接收物品的函数。
     * 该函数旨在实现提醒管理员接收特定物品的功能。
     */
    @Override
    @Scheduled(cron = "0 0 4 * * *")
    public void remindToReceiveItem() {
        // 查询当前书籍是否达到阈值，或者时间到期
        // 时间检测
        boolean remind = DayOfWeek.from(LocalDate.now()) == DayOfWeek.SATURDAY;

        List<Member> normalMemberList = jsqlClient.createQuery(memberTable)
                .where(
                        memberTable.status().eq(Member.Status.NORMAL)
                ).select(
                        memberTable.fetch(
                                Fetchers.MEMBER_FETCHER
                                        .responsibilityArea()
                                        .email()
                                        .name()
                                        .phone())
                ).execute();

        // 遍历管理员列表，查询收集的书籍信息
        for (Member member : normalMemberList) {
            if (member.responsibilityArea().isEmpty()) {
                continue;
            }
            for (Address address : member.responsibilityArea()) {
                List<Item> itemList = jsqlClient.createQuery(itemTable)
                        .where(
                                itemTable.user().addressId().eq(address.id())
                        ).where(
                                itemTable.status().eq(Item.Status.IN_USER)
                        ).select(
                                itemTable.fetch(
                                        Fetchers.ITEM_FETCHER
                                                .name()
                                )
                        ).execute();

                // 未达到，直接退出
                if (itemList.isEmpty()) {
                    continue;
                    // 达到，给管理员发送消息
                } else if (itemList.size() > memberWorkProperty.getItemGetThreshold()) {
                    remind = true;
                }

                // 配置物件号
                for (Item item : itemList) {
                    ItemCodeSubstance itemCodeSubstance = new ItemCodeSubstance();
                    ItemCodeGenerator.generateItemCode(item, itemCodeSubstance);

                    // 保存对应好的物件号
                    jsqlClient.save(itemCodeSubstance.toEntity());
                }

                String confirmPath = RandomUtil.randomUrlPath(member.id(), LocalDate.now());

                MemberConfirmSubstanceInput memberConfirmSubstanceInput = new MemberConfirmSubstanceInput();
                memberConfirmSubstanceInput.setMember(new MemberConfirmSubstanceInput.TargetOf_member());
                memberConfirmSubstanceInput.getMember().setId(member.id());
                memberConfirmSubstanceInput.setConfirmCode(confirmPath);
                memberConfirmSubstanceInput.setStatus(MemberConfirm.Status.WAIT_FOR_CONFIRM);
                memberConfirmSubstanceInput.setItems(itemList.stream().map(item -> {
                    MemberConfirmSubstanceInput.TargetOf_items targetOfItems = new MemberConfirmSubstanceInput.TargetOf_items();
                    targetOfItems.setId(item.id());
                    return targetOfItems;
                }).toList());

                jsqlClient.save(memberConfirmSubstanceInput);

                // 消息：提醒管理员接收书籍，书籍数量，名称，地点
                if (remind) {
                    // 发送提醒消息
                    mailUtil.sendMail(
                            member.email(),
                            "书籍接收提醒",
                            "您有书籍需要接收，请及时处理。详情请见："  // TODO 添加地址生成逻辑
                    );
                }
            }
        }
    }

    @Override
    public List<MemberConfirm> queryItems(String queryId) {
        List<MemberConfirm> list = jsqlClient.createQuery(memberConfirmTable)
                .where(
                        memberConfirmTable.confirmCode().eq(queryId)
                )
                .select(
                        memberConfirmTable.fetch(
                                Fetchers.MEMBER_CONFIRM_FETCHER
                                        .member(
                                                Fetchers.MEMBER_FETCHER
                                                        .name()
                                                        .profileUrl()
                                                        .status()
                                        ).status())
                ).execute();

        if (list.isEmpty()) {
            throw new ConfirmCodeInvaildException();
        }

        if (list.size() > 1) {
            throw new UnexpectedException();
        }

        return list;
    }

    @Override
    public void confirmToReceive(String confirmCode) {
        List<MemberConfirm> list = jsqlClient.createQuery(memberConfirmTable)
                .where(memberConfirmTable.confirmCode().eq(confirmCode))
                .select(
                        memberConfirmTable.fetch(
                                Fetchers.MEMBER_CONFIRM_FETCHER
                                        .items(Fetchers.ITEM_FETCHER
                                                .status()
                                        )
                        )
                ).execute();

        if (list.isEmpty()) {
            throw new ConfirmCodeInvaildException();
        }

        if (list.size() > 2) {
            throw new UnexpectedException();
        }

        Collection<Item> items = list.getFirst().items();

        List<ItemUpdateStatus> itemsForUpdateStatus = items.stream().map(item -> {
            if (item.status() != Item.Status.IN_USER) {
                throw new ItemStatusException(
                        ItemConstant.ITEM_STATUS_EXCEPTION +
                                ": " +
                                item.id() +
                                "(status: " +
                                item.status() +
                                ")");
            }

            return new ItemUpdateStatus();
        }).toList();

        jsqlClient.update(itemsForUpdateStatus);

        jsqlClient.createUpdate(memberConfirmTable)
                .set(memberConfirmTable.status(), MemberConfirm.Status.CONFIRMED)
                .where(memberConfirmTable.id().eq(list.getFirst().id()));
    }

    @Override
    public void confirmToDeliver(ItemForConfirm itemForConfirm) {
        int id = itemForConfirm.getMemberConfirm().getMember().getId();

        Collection<Integer> list = jsqlClient.createQuery(memberConfirmTable)
                .where(
                        memberConfirmTable.member().id().eq(id))
                .select(memberConfirmTable.id())
                .execute();

        if (list.size() != 1) {
            throw new ConfirmCodeInvaildException();
        }

        jsqlClient.createUpdate(memberConfirmTable)
                .set(memberConfirmTable.status(), MemberConfirm.Status.CONFIRMED);
    }

    @Override
    @Scheduled(cron = "0 0 4 * * *")
    public void remindToDeliver() {
        boolean remind;

        remind = DayOfWeek.from(LocalDate.now()) == DayOfWeek.SATURDAY;

        // TODO 待优化
        Collection<User> list = jsqlClient.createQuery(userTable)
                .groupBy(userTable.addressId())
                .groupBy(userTable.asTableEx().order().items().memberConfirmId())
                .select(userTable.fetch(
                        Fetchers.USER_FETCHER
                                .address()
                                .order(
                                        Fetchers.ORDER_FETCHER
                                                .items(
                                                        Fetchers.ITEM_FETCHER
                                                                .name()
                                                                .status()
                                                                .images(Fetchers.ITEM_IMAGE_FETCHER
                                                                        .imageUrl())
                                                                .memberConfirm(
                                                                        Fetchers.MEMBER_CONFIRM_FETCHER
                                                                                .member()
                                                                )
                                                )
                                                .status()
                                )
                )).distinct().execute();

        HashMap<Integer, List<Item>> itemListHashMap = new HashMap<>();

        list.forEach(user -> {
            user.items().forEach(item -> {
                if (itemListHashMap.get(item.memberConfirm().member().id()) != null) {
                    itemListHashMap.put(item.memberConfirm().member().id(), new ArrayList<>());
                } else {
                    itemListHashMap.get(item.memberConfirm().member().id()).add(item);
                }
            });
        });

        for (Integer memberId : itemListHashMap.keySet()) {
            remind = (remind && !itemListHashMap.get(memberId).isEmpty()) || itemListHashMap.get(memberId).size() > memberWorkProperty.getItemDeliverThreshold();
            // 消息：提醒管理员接收书籍，书籍数量，名称，地点
            if (remind) {
                // 发送提醒消息
                mailUtil.sendMail(
                        jsqlClient.createQuery(memberTable).where(memberTable.id().eq(memberId)).select(memberTable.email()).execute().getFirst(),
                        "书籍配送提醒",
                        "您有书籍需要配送，请及时处理。详情请见："  // TODO 添加地址生成逻辑
                );
            }
        }
    }
}
