package com.moyanshushe.service;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/22 上午17:59
    @Description: 

*/

import com.moyanshushe.model.dto.comment_likes.CommentLikeForAdd;
import com.moyanshushe.model.dto.comment_likes.CommentLikeForDelete;

public interface CommentLikeService {
    void add(CommentLikeForAdd commentLike);

    void delete(CommentLikeForDelete commentLike);
}
