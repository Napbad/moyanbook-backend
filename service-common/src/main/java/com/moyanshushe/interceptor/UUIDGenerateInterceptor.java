package com.moyanshushe.interceptor;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/23 上午11:31
    @Description: 

*/

import com.moyanshushe.model.entity.Order;
import com.moyanshushe.model.entity.OrderDraft;
import org.babyfish.jimmer.sql.DraftInterceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerateInterceptor implements DraftInterceptor<Order, OrderDraft> {

    public UUIDGenerateInterceptor() {
    }

    @Override
    public void beforeSave(@NotNull OrderDraft orderDraft, @Nullable Order original) {
//        if (original == null) {
//            orderDraft.setOrderId(UUID.randomUUID());
//        }
    }
}
