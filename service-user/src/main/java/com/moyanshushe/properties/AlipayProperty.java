package com.moyanshushe.properties;

/*
    @Author: Napbad
    @Version: 0.1
    @Date: 8/5/24 3:46 PM
    @Description:
         *类名：AlipayConfig
         *功能：基础配置类
         *详细：设置帐户有关信息及返回路径
*/
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "moyanshushe.alipay")
public class AlipayProperty {

    // Application Id, Your Appid, Receiver Account Is Also Your Appid Corresponding Alipay Account
    private String appId = "9021000137617639";

    // Merchant Private Key, Your Pkcs8 Format Rsa2 Private Key
    private String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCVjXrVm7542nWqrbyGUzd3e4IPhytLLPvKMeWEKuTeafXQmCSRxwy04ELmDyBwlKisSY3poKmX2WhFRucDQo4NGkimsF6Qp72MJurn8y4QzOHHSyJd+HgqM4JoHWCTItY+jVLK9EwhzgRklrGC1xvgfKVCdGuHfZHkZAuIj5IRb8J24WB3FYE1qRVFfAk79PW51N6ysMM07jBiZahIz9Ip5/54kVk53kNRal14Z2uEnv4ZxytewLG1dXNzQY79n9q7/suRclU9wPVZaq6l0K2bMOrEQ9rykAoAUnoFNJaTpeQdBc/f+eSYun5J0+ngFX8+bLJPmn9lGZK7hetUIWr7AgMBAAECggEAPvflAk+jMSt6Y7TnLf+X2R0NtBALsIluMsIziySsXOFseLm2nBKdTQMn58nytLRDGNeXgwj8n00Q1Cp4Vi0NtoKcmGKmTm3yJzKzsZn4iWZaohkGjV5y6gHqJIBKC2mdn5TNiVq3O3yM6PVyJE8CE9EnnAV8yP2cEff1yzf4HpK0s5vI7TWi8DBvWepGBYiI2bZi482mviKO5C/mFD7Z5n54yDzGT94M3vrg/s2Y9S0O5Rsg3v/LDQELI0otno0jtYrFaoKXjgGzACQg+nM8pz6kgvRWQtU1VUKCsQYv9zZ6ibkiYcP/wKKQEGwg0kPiUFfXtr21ZBkLL3qs/r4u0QKBgQDSIgSUSZ4nHESgOx6VKhrP6tlA1Q6vfv2+0+yJpjOyfFmFnyncyTogs4kTel9oG0zv1DVOaOhFRZMwlCkDHtaXq22R5rasoNdUs0yaUbap9MITZWwJlsTnjCxXFN984s3pKptqmm80hghgDhupCR3PsiaBfkevCyaAR6BxlGZqzQKBgQC2Mk8bxK6pjWJPIT8d8LoLXZ7RmDu5UFvBOUfo9Hb+i5ROEu2YRCS7PRUJ2flR9w9oCcR/0HOPOEY7ocwcYvHWvFfAldJeeysd0h5wq5EWpU7DKwfAhL+vTGy4H5VPJxIKWHOGB++5EPLeItFFqQm3ML36zKJuXZBLDvW042s85wKBgEDBQu//j8E1sOA/17jX9NNF6BVWkuP6GnAIFsiEc0H5mAzZc6kaSdNyADgEagpG/3qYxqSXJ5zRR/6cadcQVcj/hxCDLKAeIL11U5HqxIOzbWtUwepij+NbCv7667QsoI/OFX7QgJyLAWG4d0WsDYfHup4FMEn9AaxeZk8aEEWxAoGBAIjjHYqYaD4daqpXKUu+bJlTepqwzNZ7aKSw+7UrzyeKCR/rmrVXJV3EE8V2Dpsli164T/lTzS7isenlsyv0rwyOL0QTjJ3N3mQmVRaD4JtqEdYSw11Cn53+RoTDvfNdQHGjaRBfcB3UfRSmhiLXUORg66u+CZ/wDhRGphaE8zIrAoGBAKtJ9GsxC7Wh+Acpiw//ABNdymDPDsw7A4l96mGP8hD3nYMK+Prq2csU7Ygpp13ug9lpEzZNGttTYF0QKJO40GvOLmZ0DXZQOLJ3uf06aDLTvhkpYm3bpCoZ0M3WcxQhLHz/q4WgXc6HSl7aOztNe7h6jATNt6jD+d5rMTvou5il";

    // Alipay Public Key, View Address: Https://Openhome.Alipay.Com/Platform/Keymanage.Htm Corresponding Appid Under Alipay Public Key.
    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmQsc1GXhHzEoQm5XnTNlk/MaC/HhOwb6kEFboRj1B+E3tjwTQPeshVnlmIFEygFR2STlJr8cRtRXO3nfZyR0Wrm+5ODpvemV/aLInDosl8o1UQQZMCbPT/UCxTmF0TB/RF11ykaI3bRCJdzhBHZAfVAU6x96bjkonEc6LqmQlKvFMTt+AOiDjvJ+BNIKyaeqPBuUD29yHZAgG4+MHSHsJvQ8HmAw0mBvAoZCCSKd44v4k51CDLu0jpJzw3CwNAT7I21i1VfRGZ/noT4XLvwnoYJ2h60efopY8iuqlSdJ7UzPT1LktUZHbuHSHiHbFmYwlzu5run/scwmBsSbtZCbAQIDAQAB";

    // Server Asynchronous Notification Page Path Requires Http://Format Of Complete Path, Cannot Add? Id=123 This Kind Of Custom Parameters, Must Be Able To Be Normally Accessed On The Internet
    private String notifyUrl = "http://Engineering Public Network Access Address/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // Page Jump Synchronous Notification Page Path Requires Http://Format Of Complete Path, Cannot Add? Id=123 This Kind Of Custom Parameters, Must Be Able To Be Normally Accessed On The Internet
    private String returnUrl = "http://Engineering Public Network Access Address/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // Signature Method
    private String signType = "RSA2";

    // Character Encoding Format
    private String charset = "utf-8";

    // Alipay Gateway
    private String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    // Log
    private String logPath = "./alipay.log";
}
