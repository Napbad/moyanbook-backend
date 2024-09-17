package com.moyanshushe.utils;

/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/5/24 5:03 PM
    @Description: 

*/

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;

import java.io.File;

public class AlipayTest {

    public static void main(String[] args) throws Exception {
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(getOptions());
        try {
            // 2. 发起API调用（以创建当面付收款二维码为例）
            AlipayTradePrecreateResponse response = Payment.FaceToFace()
                    .preCreate("Apple iPhone11 128G", "223419465567890", "5799.00");
            // 3. 处理响应或异常
            if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
            } else {
                System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static Config getOptions() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";

        config.appId = "9021000137617639";
        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey =
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCVjXrVm7542nWqrbyGUzd3e4IPhytLLPvKMeWEKuTeafXQmCSRxwy04ELmDyBwlKisSY3poKmX2WhFRucDQo4NGkimsF6Qp72MJurn8y4QzOHHSyJd+HgqM4JoHWCTItY+jVLK9EwhzgRklrGC1xvgfKVCdGuHfZHkZAuIj5IRb8J24WB3FYE1qRVFfAk79PW51N6ysMM07jBiZahIz9Ip5/54kVk53kNRal14Z2uEnv4ZxytewLG1dXNzQY79n9q7/suRclU9wPVZaq6l0K2bMOrEQ9rykAoAUnoFNJaTpeQdBc/f+eSYun5J0+ngFX8+bLJPmn9lGZK7hetUIWr7AgMBAAECggEAPvflAk+jMSt6Y7TnLf+X2R0NtBALsIluMsIziySsXOFseLm2nBKdTQMn58nytLRDGNeXgwj8n00Q1Cp4Vi0NtoKcmGKmTm3yJzKzsZn4iWZaohkGjV5y6gHqJIBKC2mdn5TNiVq3O3yM6PVyJE8CE9EnnAV8yP2cEff1yzf4HpK0s5vI7TWi8DBvWepGBYiI2bZi482mviKO5C/mFD7Z5n54yDzGT94M3vrg/s2Y9S0O5Rsg3v/LDQELI0otno0jtYrFaoKXjgGzACQg+nM8pz6kgvRWQtU1VUKCsQYv9zZ6ibkiYcP/wKKQEGwg0kPiUFfXtr21ZBkLL3qs/r4u0QKBgQDSIgSUSZ4nHESgOx6VKhrP6tlA1Q6vfv2+0+yJpjOyfFmFnyncyTogs4kTel9oG0zv1DVOaOhFRZMwlCkDHtaXq22R5rasoNdUs0yaUbap9MITZWwJlsTnjCxXFN984s3pKptqmm80hghgDhupCR3PsiaBfkevCyaAR6BxlGZqzQKBgQC2Mk8bxK6pjWJPIT8d8LoLXZ7RmDu5UFvBOUfo9Hb+i5ROEu2YRCS7PRUJ2flR9w9oCcR/0HOPOEY7ocwcYvHWvFfAldJeeysd0h5wq5EWpU7DKwfAhL+vTGy4H5VPJxIKWHOGB++5EPLeItFFqQm3ML36zKJuXZBLDvW042s85wKBgEDBQu//j8E1sOA/17jX9NNF6BVWkuP6GnAIFsiEc0H5mAzZc6kaSdNyADgEagpG/3qYxqSXJ5zRR/6cadcQVcj/hxCDLKAeIL11U5HqxIOzbWtUwepij+NbCv7667QsoI/OFX7QgJyLAWG4d0WsDYfHup4FMEn9AaxeZk8aEEWxAoGBAIjjHYqYaD4daqpXKUu+bJlTepqwzNZ7aKSw+7UrzyeKCR/rmrVXJV3EE8V2Dpsli164T/lTzS7isenlsyv0rwyOL0QTjJ3N3mQmVRaD4JtqEdYSw11Cn53+RoTDvfNdQHGjaRBfcB3UfRSmhiLXUORg66u+CZ/wDhRGphaE8zIrAoGBAKtJ9GsxC7Wh+Acpiw//ABNdymDPDsw7A4l96mGP8hD3nYMK+Prq2csU7Ygpp13ug9lpEzZNGttTYF0QKJO40GvOLmZ0DXZQOLJ3uf06aDLTvhkpYm3bpCoZ0M3WcxQhLHz/q4WgXc6HSl7aOztNe7h6jATNt6jD+d5rMTvou5il";
        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
        // config.merchantCertPath = "<-- 请填写您的应用公钥证书文件路径，例如：/foo/appCertPublicKey_2019051064521003.crt -->";
        // config.alipayCertPath = "<-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->";
        // config.alipayRootCertPath = "<-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->";
        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
         config.alipayPublicKey =
                 "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmQsc1GXhHzEoQm5XnTNlk/MaC/HhOwb6kEFboRj1B+E3tjwTQPeshVnlmIFEygFR2STlJr8cRtRXO3nfZyR0Wrm+5ODpvemV/aLInDosl8o1UQQZMCbPT/UCxTmF0TB/RF11ykaI3bRCJdzhBHZAfVAU6x96bjkonEc6LqmQlKvFMTt+AOiDjvJ+BNIKyaeqPBuUD29yHZAgG4+MHSHsJvQ8HmAw0mBvAoZCCSKd44v4k51CDLu0jpJzw3CwNAT7I21i1VfRGZ/noT4XLvwnoYJ2h60efopY8iuqlSdJ7UzPT1LktUZHbuHSHiHbFmYwlzu5run/scwmBsSbtZCbAQIDAQAB";
        //可设置异步通知接收服务地址（可选）
         config.notifyUrl = "";
        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
         config.encryptKey = "+Gtnp5EBuDFP2aRXN9mcBw==";
        return config;
    }

}
