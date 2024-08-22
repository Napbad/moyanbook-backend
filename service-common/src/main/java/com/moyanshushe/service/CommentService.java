package com.moyanshushe.service;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/22 上午17:22
    @Description: 

*/

import com.moyanshushe.model.dto.comment.*;
import com.moyanshushe.model.entity.Comment;
import org.babyfish.jimmer.Page;

public interface CommentService {
    void add(CommentForAdd comment);

    Page<Comment> query(CommentSpecification specification);

    void update(CommentForUpdate comment);

    void delete(CommentForDelete comment);

    Page<Comment> queryPublic(PublicCommentSpecification specification);
}
