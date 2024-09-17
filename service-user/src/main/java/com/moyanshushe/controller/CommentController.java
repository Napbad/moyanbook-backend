package com.moyanshushe.controller;



/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/12/24
    @Description: 

*/

import com.moyanshushe.client.CommonServiceClient;
import com.moyanshushe.exception.NoAuthorityException;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.comment.CommentForAdd;
import com.moyanshushe.model.dto.comment.CommentForDelete;
import com.moyanshushe.model.dto.comment.CommentForUpdate;
import com.moyanshushe.model.dto.comment.CommentSpecification;
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
@RequestMapping({"/comment"})
@RequiredArgsConstructor
public class CommentController {
    private final CommonServiceClient commonServiceClient;

    /**
     * 添加评论
     *
     * @param comment 评论信息
     *                CommentForAdd {
     *                content
     *                commenterId!
     *                itemId!
     *                parentId
     *                commentTime!
     *                likes
     *                dislikes
     *                }
     * @return 添加结果
     */
    @Api
    @PostMapping("/add")
    public ResponseEntity<Result> addComment(@RequestBody CommentForAdd comment) {

        if (!Objects.equals(comment.getCommenterId(), UserContext.getUserId())) {
            throw new NoAuthorityException();
        }

        return commonServiceClient.addComment(comment);
    }

    /**
     * 删除评论
     *
     * @param comment 评论信息
     * @return 删除结果
     */
    @Api
    @PostMapping("/delete")
    public ResponseEntity<Result> deleteComment(@RequestBody CommentForDelete comment) {

        if (!Objects.equals(comment.getCommenterId(), UserContext.getUserId())) {
            throw new NoAuthorityException();
        }

        return commonServiceClient.deleteComment(comment);
    }

    /**
     * 更新评论
     *
     * @param comment 评论信息
     *                CommentForUpdate {
     *                id!
     *                content
     *                commenterId!
     *                }
     * @return 更新结果
     */
    @Api
    @PostMapping("/update")
    public ResponseEntity<Result> updateComment(@RequestBody CommentForUpdate comment) {

        if (!Objects.equals(comment.getCommenterId(), UserContext.getUserId())) {
            throw new NoAuthorityException();
        }

        return commonServiceClient.updateComment(comment);
    }

    @Api
    @PostMapping("/query")
    public ResponseEntity<Result> queryComment(@RequestBody CommentSpecification specification) {
        return commonServiceClient.queryComment(specification);
    }
}
