package com.moyanshushe.controller;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/22 上午17:57
    @Description: 

*/

import com.moyanshushe.constant.CommentConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.comment.CommentForAdd;
import com.moyanshushe.model.dto.comment.CommentForDelete;
import com.moyanshushe.model.dto.comment.CommentForUpdate;
import com.moyanshushe.model.dto.comment.CommentSpecification;
import com.moyanshushe.model.dto.comment_likes.CommentLikeForAdd;
import com.moyanshushe.model.dto.comment_likes.CommentLikeForDelete;
import com.moyanshushe.model.dto.comment_likes.CommentLikeSpecification;
import com.moyanshushe.service.CommentLikeService;
import com.moyanshushe.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@Slf4j
@RestController
@RequestMapping("/comment-like")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    public CommentLikeController(CommentLikeService commentService) {
        this.commentLikeService = commentService;
    }

    @PostMapping("/add")
    public ResponseEntity<Result> add(@RequestBody CommentLikeForAdd commentLike) {
        commentLikeService.add(commentLike);
        return ResponseEntity.ok(Result.success(CommentConstant.COMMENT_SUCCESS));
    }

    @PostMapping("/delete")
    public ResponseEntity<Result> delete(@RequestBody CommentLikeForDelete commentLike) {
        commentLikeService.delete(commentLike);
        return ResponseEntity.ok(Result.success(CommentConstant.COMMENT_DELETE_SUCCESS));
    }

    @PostMapping("/query")
    public ResponseEntity<Result> query(@RequestBody CommentLikeSpecification specification) {
        return ResponseEntity.ok(Result.success(commentLikeService.query(specification)));
    }
}
