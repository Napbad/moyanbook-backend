package com.moyanshushe.mapper;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/22 上午17:44
    @Description: 

*/

import com.moyanshushe.model.dto.comment_history.CommentHistoryForAdd;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.stereotype.Component;

@Component
public class CommentHistoryMapper {
    private final JSqlClient jsqlClient;

    public CommentHistoryMapper(JSqlClient jsqlClient, JSqlClient jsqlClient1) {
        this.jsqlClient = jsqlClient1;
    }

    public void add(CommentHistoryForAdd commentHistory) {
        jsqlClient.save(commentHistory.toEntity());
    }
}
