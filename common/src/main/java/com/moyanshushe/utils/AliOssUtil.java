package com.moyanshushe.utils;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.moyanshushe.exception.io.FileUploadException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 文件上传
     *
     * @param bytes file
     * @param objectName name
     * @return url
     */
    public String upload(byte[] bytes, String objectName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        } catch (OSSException oe) {
            log.warn("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.warn("Error Message: {} ", oe.getErrorMessage());
            log.warn("Error Code: {} ", oe.getErrorCode());
            log.warn("Request ID: {} ", oe.getRequestId());
            log.warn("Host ID: {} ", oe.getHostId());
        } catch (ClientException ce) {
            log.warn("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.warn("Error Message: {} ", ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);

        log.info("文件上传到:{}", stringBuilder.toString());

        return stringBuilder.toString();
    }

    public void checkUrlIsAliOss(String url) {
        if (url == null) {
            throw new FileUploadException("上传失败,文件地址为空");
        }
        if (!url.startsWith("https://" + bucketName + "." +endpoint + "/")) {
            throw new FileUploadException("上传失败,文件地址不正确");
        }
    }
}
