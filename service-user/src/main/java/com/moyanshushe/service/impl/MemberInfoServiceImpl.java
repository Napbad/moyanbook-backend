package com.moyanshushe.service.impl;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/8 上午0:08
    @Description: 

*/

import com.moyanshushe.model.dto.member.PublicMemberSpecification;
import com.moyanshushe.model.dto.member.PublicMemberView;
import com.moyanshushe.model.entity.Member;
import com.moyanshushe.model.entity.MemberTable;
import com.moyanshushe.service.MemberInfoService;
import lombok.RequiredArgsConstructor;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService {

    private final JSqlClient jsqlClient;

    private final MemberTable table = new MemberTable();

    @NotNull
    @Override
    public Page<Member> queryMember(PublicMemberSpecification memberSpecification) {
        return jsqlClient.createQuery(table)
                .where(memberSpecification)
                .select(
                        table.fetch(
                                PublicMemberView.METADATA.getFetcher()
                        )
                ).fetchPage(
                        memberSpecification.getPage() != null ? memberSpecification.getPage() : 0,
                        memberSpecification.getPageSize() != null ? memberSpecification.getPageSize() : 10
                );
    }
}
