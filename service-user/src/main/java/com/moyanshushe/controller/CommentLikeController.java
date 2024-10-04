package com.moyanshushe.controller;

import com.moyanshushe.client.CommonServiceClient;
import com.moyanshushe.constant.CommentConstant;
import com.moyanshushe.exception.NoAuthorityException;
import com.moyanshushe.exception.common.InputInvalidException;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.comment_likes.CommentLikeForAdd;
import com.moyanshushe.model.dto.comment_likes.CommentLikeForDelete;
import com.moyanshushe.model.dto.comment_likes.CommentLikeSpecification;
import com.moyanshushe.service.CommentLikeService;
import com.moyanshushe.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 负责处理评论点赞相关的HTTP请求
 */
@Api
@RestController
@RequestMapping({""})
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    /**
     * 添加点赞
     *
     * @param commentLike 点赞信息
     * @return 添加结果
     */
    @Api
    @PostMapping("/user/comment-like/add")
    public ResponseEntity<Result> addCommentLike(@RequestBody CommentLikeForAdd commentLike) {
        if (!Objects.equals(commentLike.getUserId(), UserContext.getUserId())) {
            throw new NoAuthorityException();
        }
        commentLikeService.add(commentLike);
        return ResponseEntity.ok(Result.success(CommentConstant.COMMENT_LIKE_SUCCESS));
    }

    /**
     * 删除点赞
     *
     * @param commentLike 点赞信息
     * @return 删除结果
     */
    @Api
    @PostMapping("/user/comment-like/delete")
    public ResponseEntity<Result> deleteCommentLike(@RequestBody CommentLikeForDelete commentLike) {
        if (!Objects.equals(commentLike.getUserId(), UserContext.getUserId())) {
            throw new NoAuthorityException();
        }
        commentLikeService.delete(commentLike);
        return ResponseEntity.ok(Result.success(CommentConstant.COMMENT_DELETE_SUCCESS));
    }

    /**
     * 查询点赞情况
     *
     * @param specification 查询条件
     * @return 点赞过的评论
     */
    @Api
    @PostMapping("/user/comment-like/query")
    public ResponseEntity<Result> queryCommentLike(@RequestBody CommentLikeSpecification specification) {
        return ResponseEntity.ok(Result.success(commentLikeService.query(specification)));
    }
}
