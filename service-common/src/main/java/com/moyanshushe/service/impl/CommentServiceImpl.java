package com.moyanshushe.service.impl;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/22 上午17:22
    @Description: 

*/

import com.moyanshushe.mapper.CommentHistoryMapper;
import com.moyanshushe.mapper.CommentMapper;
import com.moyanshushe.model.dto.comment.*;
import com.moyanshushe.model.dto.comment_history.CommentHistoryForAdd;
import com.moyanshushe.model.entity.Comment;
import com.moyanshushe.model.entity.Fetchers;
import com.moyanshushe.service.CommentService;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentHistoryMapper commentHistoryMapper;

    public CommentServiceImpl(CommentMapper commentMapper, CommentHistoryMapper commentHistoryMapper) {
        this.commentMapper = commentMapper;
        this.commentHistoryMapper = commentHistoryMapper;
    }

    @Override
    public void add(CommentForAdd comment) {
        commentMapper.add(comment);
    }

    @Override
    public Page<Comment> query(CommentSpecification specification) {
        return commentMapper.query(specification,
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
                        ).commentLike(
                                Fetchers.COMMENT_LIKE_FETCHER
                                        .user(
                                                Fetchers.USER_FETCHER
                                                        .name()
                                                        .profileUrl()
                                                        .type()
                                                        .gender()
                                        )
                                        .likeTime()
                        )
        );
    }

    @Override
    @Transactional
    public void update(CommentForUpdate comment) {
        CommentHistoryForAdd commentHistoryForAdd = new CommentHistoryForAdd();

        SimpleSaveResult<Comment> saveResult = commentMapper.update(comment);

        Comment originalEntity = saveResult.getOriginalEntity();

        if (originalEntity != null) {
            commentHistoryForAdd.setCommentId(originalEntity.id());
            commentHistoryForAdd.setContent(originalEntity.content());
            commentHistoryForAdd.setModifiedBy(originalEntity.commenterId());
        }

        commentHistoryMapper.add(commentHistoryForAdd);
    }

    @Override
    public void delete(CommentForDelete comment) {
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
