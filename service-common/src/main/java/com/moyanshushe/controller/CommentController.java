package com.moyanshushe.controller;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/22 上午17:21
    @Description: 

*/


import com.moyanshushe.constant.CommentConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.comment.*;
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
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public ResponseEntity<Result> add(@RequestBody CommentForAdd comment) {
        commentService.add(comment);
        return ResponseEntity.ok(Result.success(CommentConstant.COMMENT_SUCCESS));
    }

    @PostMapping("/query")
    public ResponseEntity<Result> query(@RequestBody CommentSpecification specification) {
        return ResponseEntity.ok(Result.success(commentService.query(specification)));
    }

    @PostMapping("/query-public")
    public ResponseEntity<Result> queryPublic(@RequestBody PublicCommentSpecification specification) {
        return ResponseEntity.ok(Result.success(commentService.queryPublic(specification)));
    }

    @PostMapping("/update")
    public ResponseEntity<Result> update(@RequestBody CommentForUpdate comment) {
        commentService.update(comment);
        return ResponseEntity.ok(Result.success(CommentConstant.COMMENT_UPDATE_SUCCESS));
    }

    @PostMapping("/delete")
    public ResponseEntity<Result> delete(@RequestBody CommentForDelete comment) {
        commentService.delete(comment);
        return ResponseEntity.ok(Result.success(CommentConstant.COMMENT_DELETE_SUCCESS));
    }
}
