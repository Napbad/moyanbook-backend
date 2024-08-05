package com.moyanshushe.utils.storage;

import com.moyanshushe.constant.FileConstant;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class FileUtil {

    public static boolean checkFileNameFormat(String name) {
        return name != null && name.endsWith(FileConstant.WEBP_SUFFIX);
    }

    private FileUtil() {}
}
