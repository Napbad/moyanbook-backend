//package com.moyanshushe.utils.verify;
//
///*
// * Author: Napbad
// * Version: 1.0
// */
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//
//import java.io.UnsupportedEncodingException;
//
///**
// * 邮件工具类，用于发送邮件。
// */
//@Slf4j
//@Component
//public class MailUtil {
//    public static final String FROM = "3242541948@qq.com";
//    public static final String FROM_NAME = "MoYanShushe";
//
//    // JavaMailSender接口实例，用于发送邮件
//    final JavaMailSender javaMailSender;
//
//    /**
//     * 构造函数，注入JavaMailSender。
//     *
//     * @param javaMailSender 用于发送邮件的JavaMailSender实例
//     */
//    public MailUtil(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//
//    public void sendCaptcha(String captcha, String to) {
//        sendMail(to, "验证码", "您的验证码是：<br> <strong>" + captcha + "<strong> <br>请勿泄露。", true);
//    }
//
//    /**
//     * 发送简单邮件，正文默认为纯文本。
//     *
//     * @param to 收件人邮箱
//     * @param subject 邮件主题
//     * @param content 邮件正文
//     */
//    public void sendMail(String to, String subject, String content) {
//        sendMail(to, subject, content, false);
//    }
//
//    /**
//     * 发送邮件，可以选择正文是否为HTML格式。
//     *
//     * @param to 收件人邮箱
//     * @param subject 邮件主题
//     * @param content 邮件正文
//     * @param isHtml 是否将正文作为HTML发送
//     */
//    public void sendMail(String to, String subject, String content, Boolean isHtml) {
//        // 创建一个邮件消息
//        MimeMessage message = javaMailSender.createMimeMessage();
//
//        // 创建 MimeMessageHelper，用于简化邮件内容设置
//        MimeMessageHelper helper = null;
//        try {
//            helper = new MimeMessageHelper(message, false);
//
//            // 尝试发送邮件，确保发送成功
//            // 设置发件人、收件人、主题和正文
//            helper.setFrom(FROM, FROM_NAME);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(content, isHtml);
//
//            // 发送邮件
//            javaMailSender.send(message);
//
//            // 记录成功发送的日志
//            log.info("success send to: {}", to);
//
//        } catch (MessagingException e) {
//            log.error("send mail exception \n {}", e.getMessage());
//            // 发送邮件异常，抛出运行时异常
//        } catch (UnsupportedEncodingException e) {
//            log.error("unsupported encoding \n {}", e.getMessage());
//        }
//    }
//}
