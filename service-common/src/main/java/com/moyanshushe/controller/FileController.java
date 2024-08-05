package com.moyanshushe.controller;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.constant.FileConstant;
import com.moyanshushe.exception.io.FileUploadException;
import com.moyanshushe.model.Result;
import com.moyanshushe.utils.AliOssUtil;
import com.moyanshushe.utils.storage.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Api
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    private final AliOssUtil aliOssUtil;

    // 构造函数注入OutSideProperty，用于获取文件存储路径和服务器地址
    public FileController(AliOssUtil aliOssUtil) {
        this.aliOssUtil = aliOssUtil;
    }

    /**
     * 异步上传图片
     *
     * @param file 接收上传的文件
     * @return CompletableFuture<Result> 异步返回文件上传结果，其中包含文件的访问URL
     */
    @Api // 标识异步执行的API接口
    @Async // 异步执行
    @PostMapping("/upload/image")
    public CompletableFuture<Result> uploadImage(@RequestParam("file") MultipartFile file) {
        // 获取文件原始名称
        String filename = file.getOriginalFilename();

        // 检查文件名格式是否合法
        if (!FileUtil.checkFileNameFormat(Objects.requireNonNull(filename))) {
            throw new FileUploadException(FileConstant.FILE_NOT_SUPPORTED);
        }

        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new FileUploadException(FileConstant.FILE_IS_NULL);
        }

        try {
            UUID uuid = UUID.randomUUID();

            int index = filename.lastIndexOf('.');

            filename = uuid + filename.substring(index);

            String path = aliOssUtil.upload(file.getBytes(), filename);

            // 记录文件上传日志
            log.info("文件上传成功: {}", path);
            // 返回文件的访问URL
            return CompletableFuture.completedFuture(Result.success(path));
        } catch (IOException e) {
            throw new FileUploadException();
        }
    }
}
