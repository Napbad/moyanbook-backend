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
import com.moyanshushe.model.dto.comment_likes.CommentLikeSpecification;
import com.moyanshushe.model.entity.*;
import com.moyanshushe.service.CommentLikeService;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Comment first = queryOneComment(commentLike.getCommentId());

        List<CommentLike> commentLikeList = jSqlClient.createQuery(table)
                .where(
                        table.commentId().eq(commentLike.getCommentId()),
                        table.userId().eq(commentLike.getUserId())
                ).select(
                        table.fetch(Fetchers.COMMENT_LIKE_FETCHER.user())
                ).execute();
        if (commentLikeList.isEmpty()) {
            throw new DBException();
        }

        jSqlClient.deleteById(CommentLike.class, commentLikeList.getFirst().id());
        jSqlClient.save(
                Objects.createComment(
                        draft -> {
                            draft.setId(first.id());
                            draft.setLikes(first.likes() - 1);
                        }
                )
        );

    }

    @Override
    public Page<CommentLike> query(CommentLikeSpecification specification) {

        return jSqlClient.createQuery(table)
                .where(specification)
                .select(
                        table.fetch(
                                Fetchers.COMMENT_LIKE_FETCHER
                                        .comment(
                                        Fetchers.COMMENT_FETCHER
                                                .label(
                                                        Fetchers.LABEL_FETCHER
                                                                .name()
                                                )
                                        )
                        )
                ).fetchPage(
                        specification.getPage() != null ? specification.getPage() : 1,
                        specification.getPageSize() != null ? specification.getPageSize() : 10
                );
    }

    private Comment queryOneComment(int commentId) {
        CommentSpecification commentSpecification = new CommentSpecification();
        commentSpecification.setId(commentId);
        Page<Comment> query = commentMapper.query(
                commentSpecification,
                Fetchers.COMMENT_FETCHER.allScalarFields()
        );

        if (query.getTotalRowCount() != 1) {
            throw new DBException();
        }

        return query.getRows().getFirst();
    }
}
