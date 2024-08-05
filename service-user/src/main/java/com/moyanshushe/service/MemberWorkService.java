package com.moyanshushe.service;

import com.moyanshushe.model.dto.item.ItemForConfirm;
import com.moyanshushe.model.entity.MemberConfirm;

import java.util.List;

/*
 * Author: Napbad
 * Version: 1.0
 */
public interface MemberWorkService {

    void remindToReceiveItem();

    List<MemberConfirm> queryItems(String queryId);

    void confirmToReceive(String confirmCode);

    void confirmToDeliver(ItemForConfirm itemForConfirm);

    void remindToDeliver();
}
