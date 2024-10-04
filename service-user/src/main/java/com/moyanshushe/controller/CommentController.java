package com.moyanshushe.controller;



/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/12/24
    @Description: 

*/

import com.moyanshushe.client.CommonServiceClient;
import com.moyanshushe.constant.CommentConstant;
import com.moyanshushe.exception.NoAuthorityException;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.comment.CommentForAdd;
import com.moyanshushe.model.dto.comment.CommentForDelete;
import com.moyanshushe.model.dto.comment.CommentForUpdate;
import com.moyanshushe.model.dto.comment.CommentSpecification;
import com.moyanshushe.service.CommentService;
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
 * 评论控制器类，负责处理评论相关的HTTP请求
 */
@Api
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 添加评论
     *
     * @param comment 评论信息
     * @return 添加结果
     */
    @Api
    @PostMapping("/user/comment/add")
    public ResponseEntity<Result> addComment(@RequestBody CommentForAdd comment) {

        if (!Objects.equals(comment.getCommenterId(), UserContext.getUserId())) {
            throw new NoAuthorityException();
        }

        commentService.add(comment);
        return ResponseEntity.ok(Result.success(CommentConstant.COMMENT_SUCCESS));
    }

    /**
     * 删除评论
     *
     * @param comment 评论信息
     * @return 删除结果
     */
    @Api
    @PostMapping("/user/comment/delete")
    public ResponseEntity<Result> deleteComment(@RequestBody CommentForDelete comment) {

        if (!Objects.equals(comment.getCommenterId(), UserContext.getUserId())) {
            throw new NoAuthorityException();
        }

        commentService.delete(comment);
        return ResponseEntity.ok(Result.success(CommentConstant.COMMENT_DELETE_SUCCESS));

    }

    /**
     * 更新评论
     *
     * @param comment 评论信息
     * @return 更新结果
     */
    @Api
    @PostMapping("/user/comment/update")
    public ResponseEntity<Result> updateComment(@RequestBody CommentForUpdate comment) {

        if (!Objects.equals(comment.getCommenterId(), UserContext.getUserId())) {
            throw new NoAuthorityException();
        }

        return ResponseEntity.ok(Result.success(
                CommentConstant.COMMENT_UPDATE_SUCCESS,
                commentService.update(comment)));

    }

    @Api
    @PostMapping("/user/comment/query")
    public ResponseEntity<Result> queryComment(@RequestBody CommentSpecification specification) {
        return ResponseEntity.ok(
                Result.success(
                        CommentConstant.COMMENT_QUERY_SUCCESS,
                        commentService.query(specification)));
    }
}
