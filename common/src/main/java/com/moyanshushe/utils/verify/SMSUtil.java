package com.moyanshushe.utils.verify;//package com.moyanshushe.utils.verify;
//
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.profile.DefaultProfile;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.HashMap;
//import java.util.Map;
//
///*
// * Author: Napbad
// * Version: 1.0
// * Description: 使用阿里云短信服务来发送指定短信信息
// */
//
//// TODO : 待完善
//@Slf4j
//public class SMSUtil {
//
//    private static final String ACCESS_KEY_ID = "your_access_key_id";
//    private static final String ACCESS_KEY_SECRET = "your_access_key_secret";
//    private static final String REGION_ID = "cn-hangzhou"; // 阿里云账号所在地域，例如"cn-hangzhou"
//    private static final String SIGN_NAME = "your_sign_name"; // 阿里云控制台创建的签名名称
//    private static final String TEMPLATE_CODE = "your_template_code"; // 阿里云控制台创建的模板CODE
//
//    private static IAcsClient client;
//
//    static {
//        DefaultProfile profile = DefaultProfile.getProfile(
//                REGION_ID,
//                ACCESS_KEY_ID,
//                ACCESS_KEY_SECRET);
//        client = new DefaultAcsClient(profile);
//    }
//
//    /**
//     * 发送短信
//     *
//     * @param phoneNumber 目标手机号码
//     * @param templateParams 模板变量，Map<String, String>形式
//     * @return SendSmsResponse 短信发送响应对象
//     * @throws ClientException 如果发送过程中出现异常，抛出此异常
//     */
//    public static SendSmsResponse sendSMS(String phoneNumber, Map<String, String> templateParams) throws ClientException {
//        SendSmsRequest request = new SendSmsRequest();
//        request.setPhoneNumbers(phoneNumber);
//        request.setSignName(SIGN_NAME);
//        request.setTemplateCode(TEMPLATE_CODE);
//        request.setTemplateParam(templateParams.toString());
//
//        return client.getAcsResponse(request);
//    }
//
//    // 示例：如何调用发送短信方法
////    public static void main(String[] args) {
////        try {
////            Map<String, String> params = new HashMap<>();
////            params.put("code", "123456"); // 假设验证码为123456
////            SendSmsResponse response = sendSMS("+861234567890", params);
////            log.info("短信发送结果：{}", response.getCode());
////            if ("OK".equals(response.getCode())) {
////                log.info("短信发送成功");
////            } else {
////                log.info("短信发送失败：{}", response.getMessage());
////            }
////        } catch (ClientException e) {
////            log.error("发送短信时发生异常：{}", e.getMessage());
////        }
////    }
//}
