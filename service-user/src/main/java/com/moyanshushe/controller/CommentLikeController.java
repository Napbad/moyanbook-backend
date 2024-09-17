package com.moyanshushe.controller;



/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/12/24
    @Description: 

*/

import com.moyanshushe.client.CommonServiceClient;
import com.moyanshushe.exception.NoAuthorityException;
import com.moyanshushe.exception.common.InputInvalidException;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.comment_likes.CommentLikeForAdd;
import com.moyanshushe.model.dto.comment_likes.CommentLikeForDelete;
import com.moyanshushe.model.dto.comment_likes.CommentLikeSpecification;
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
@RequestMapping({"/commentLike-like"})
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommonServiceClient commonServiceClient;

    /**
     * 添加点赞
     *
     * @param commentLike 点赞信息
     *                    CommentLikeForAdd {
     *                    userId！
     *                    commentId！
     *                    }
     * @return 添加结果
     */
    @Api
    @PostMapping("/add")
    public ResponseEntity<Result> addCommentLike(@RequestBody CommentLikeForAdd commentLike) {

        if (!Objects.equals(commentLike.getUserId(), UserContext.getUserId())) {
            throw new NoAuthorityException();
        }

        return commonServiceClient.addCommentLike(commentLike);
    }

    /**
     * 删除点赞
     *
     * @param commentLike 点赞信息
     *                    CommentLikeForDelete {
     *                    userId!
     *     commentId!
     * }
     * @return 删除结果
     */
    @Api
    @PostMapping("/delete")
    public ResponseEntity<Result> deleteCommentLike(@RequestBody CommentLikeForDelete commentLike) {

        if (!Objects.equals(commentLike.getUserId(), UserContext.getUserId())) {
            throw new NoAuthorityException();
        }

        return commonServiceClient.deleteCommentLike(commentLike);
    }


    /**
     * @param specification {
     *                       CommentLikeSpecification {
     *     userId
     *     commentId
     *     ge(likeDate)
     *     le(likeDate)
     *
     *     page: Int?
     *     pageSize: Int?
     * }
     * }
     * @return 点赞过的
     */
    @Api
    @PostMapping("/query")
    public ResponseEntity<Result> queryCommentLike(@RequestBody CommentLikeSpecification specification) {
        return commonServiceClient.queryCommentLike(specification);
    }
}
