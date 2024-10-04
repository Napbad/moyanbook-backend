package com.moyanshushe.service.impl;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/22 上午17:22
    @Description: 

*/

import com.moyanshushe.constant.AuthorityConstant;
import com.moyanshushe.exception.NoAuthorityException;
import com.moyanshushe.mapper.CommentHistoryMapper;
import com.moyanshushe.mapper.CommentMapper;
import com.moyanshushe.model.dto.comment.*;
import com.moyanshushe.model.dto.comment_history.CommentHistoryForAdd;
import com.moyanshushe.model.entity.Comment;
import com.moyanshushe.model.entity.CommentTable;
import com.moyanshushe.model.entity.Fetchers;
import com.moyanshushe.service.CommentService;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentHistoryMapper commentHistoryMapper;
    private final JSqlClient jSqlClient;

    private final CommentTable table;

    public CommentServiceImpl(CommentMapper commentMapper, CommentHistoryMapper commentHistoryMapper, JSqlClient jSqlClient) {
        this.commentMapper = commentMapper;
        this.commentHistoryMapper = commentHistoryMapper;
        this.jSqlClient = jSqlClient;
        table = new CommentTable();
    }

    @Override
    public void add(CommentForAdd comment) {
        commentMapper.add(comment);
    }

    @Override
    public Page<Comment> query(CommentSpecification specification) {
        return commentMapper.query(specification,
                Fetchers.COMMENT_FETCHER
                        .commenter(
                                Fetchers.USER_FETCHER
                                        .name()
                                        .profileUrl()
                                        .type()
                        )
                        .content()
                        .parentId()
                        .label(
                                Fetchers.LABEL_FETCHER
                                        .name()
                        )
                        .commentTime()
                        .likes()
                        .recursiveChildren(
                                cfg -> {
                                    cfg.depth(2);
                                }
                        )
        );
    }

    @Override
    @Transactional
    public CommentUpdateView update(CommentForUpdate comment) {
        CommentHistoryForAdd commentHistoryForAdd = new CommentHistoryForAdd();

        SimpleSaveResult<Comment> saveResult = commentMapper.update(comment);
        if (saveResult.isModified()) {
            throw new NoAuthorityException();
        }
        Comment originalEntity = saveResult.getOriginalEntity();

        if (originalEntity != null) {
            commentHistoryForAdd.setCommentId(originalEntity.id());
            commentHistoryForAdd.setContent(originalEntity.content());
            commentHistoryForAdd.setModifiedBy(originalEntity.commenterId());
        }

        commentHistoryMapper.add(commentHistoryForAdd);

        return (CommentUpdateView) (originalEntity);
    }

    @Override
    public void delete(CommentForDelete comment) {

        List<Long> executed = jSqlClient.createQuery(table)
                .where(table.id().in(comment.getIds()))
                .where(table.commenterId().eq(comment.getCommenterId()))
                .select(table.count())
                .execute();

        if (executed.getFirst() != comment.getIds().size()) {
            throw new NoAuthorityException(AuthorityConstant.NO_AUTHORIZATION);
        }

        commentMapper.delete(comment);
    }

    @Override
    public Page<Comment> queryPublic(PublicCommentSpecification specification) {
        return commentMapper.query(
                specification,
                Fetchers.COMMENT_FETCHER
                        .commenter()
                        .content()
                        .parentId()
                        .labelId()
                        .commentTime()
                        .likes()
                        .recursiveChildren(
                                cfg -> {
                                    cfg.depth(2);
                                }
                        )
        );
    }
}
