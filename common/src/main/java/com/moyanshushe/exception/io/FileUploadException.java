package com.moyanshushe.exception.io;

import com.moyanshushe.constant.FileConstant;
import com.moyanshushe.exception.BaseException;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class FileUploadException extends BaseException {
    public FileUploadException() {
        super(FileConstant.FILE_UPLOAD_FAILURE);
    }

    public FileUploadException(String msg) {
        super(msg);
    }
}
