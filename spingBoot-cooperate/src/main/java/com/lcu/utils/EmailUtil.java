package com.lcu.utils;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.util.Arrays;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    //实施邮件发送的对象
    final JavaMailSenderImpl javaMailSender;

    //模版引擎
    final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;

    @Async
    public void sendMessageToEmail(String verifyCode, String email) {
        Context context = new Context(); // 引入Template的Context
        // 设置模板中的变量（分割验证码）
        context.setVariable("verifyCode", Arrays.asList(verifyCode.split("")));
        // 第一个参数为模板的名称(html不用写全路径)
        //使用引擎解析模板 返回解析后的超文本内容 <html>.....</html>
        String process = templateEngine.process("EmailCode.html", context); // 这里不用写全路径
        //发送邮件的信息类
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            //是处理JavaMail比较顺手的组件之一，可以让你摆脱繁复的JavaMail API
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("【东西部协作】 验证码"); // 邮件的标题
            helper.setFrom(username); // 发送者
            helper.setTo(email); // 接收者
            helper.setSentDate(new Date()); // 时间
            helper.setText(process, true); // 第二个参数true表示这是一个html文本
        } catch (Exception e) {
            throw new RuntimeException( "邮件发送异常");
        }
        javaMailSender.send(mimeMessage);
    }
}
