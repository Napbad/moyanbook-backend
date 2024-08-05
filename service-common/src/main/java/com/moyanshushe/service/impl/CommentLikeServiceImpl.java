package com.moyanshushe.service.impl;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/22 上午18:00
    @Description: 

*/

import com.moyanshushe.exception.common.DBException;
import com.moyanshushe.mapper.CommentMapper;
import com.moyanshushe.model.dto.comment.CommentSpecification;
import com.moyanshushe.model.dto.comment_likes.CommentLikeForAdd;
import com.moyanshushe.model.dto.comment_likes.CommentLikeForDelete;
import com.moyanshushe.model.entity.*;
import com.moyanshushe.service.CommentLikeService;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {

    private final JSqlClient jSqlClient;
    private final CommentMapper commentMapper;
    private final CommentLikeTable table;
    private final CommentTable commentTable;

    public CommentLikeServiceImpl(JSqlClient jSqlClient, CommentMapper commentMapper) {
        this.jSqlClient = jSqlClient;
        this.commentMapper = commentMapper;
        this.table = CommentLikeTable.$;
        this.commentTable = CommentTable.$;
    }

    @Override
    @Transactional
    public void add(CommentLikeForAdd commentLike) {
        jSqlClient.save(commentLike);

        Comment first = queryOneComment(commentLike.getCommentId());
        jSqlClient.save(
                Objects.createComment(
                        draft -> {
                            draft.setId(first.id());
                            draft.setLikes(first.likes() + 1);
                        }
                )
        );
    }

    @Override
    @Transactional
    public void delete(CommentLikeForDelete commentLike) {
        jSqlClient.deleteById(CommentLike.class, commentLike.getCommentId());

        Comment first = queryOneComment(commentLike.getCommentId());
        jSqlClient.save(
                Objects.createComment(
                        draft -> {
                            draft.setId(first.id());
                            draft.setLikes(first.likes() - 1);
                        }
                )
        );

    }

    private Comment queryOneComment(int commentId) {
        CommentSpecification commentSpecification = new CommentSpecification();
        commentSpecification.setId(commentId);
        Page<Comment> query = commentMapper.query(commentSpecification);

        if (query.getTotalRowCount() != 1) {
            throw new DBException();
        }

        return query.getRows().getFirst();
    }
}
