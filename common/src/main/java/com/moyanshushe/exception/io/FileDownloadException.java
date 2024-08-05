package com.moyanshushe.exception.io;

import com.moyanshushe.exception.BaseException;
import com.moyanshushe.constant.FileConstant;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class FileDownloadException extends BaseException {
    public FileDownloadException() {
        super(FileConstant.FILE_DOWNLOAD_FAILURE);
    }
    public FileDownloadException(String msg) {
        super(msg);
    }
}
