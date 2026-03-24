package com.lcu.controller;

import ch.qos.logback.core.util.StringUtil;
import com.google.code.kaptcha.Producer;
import com.lcu.bean.JsonResponse;
import com.lcu.bean.Result;
import com.lcu.bean.User;
import com.lcu.service.UserService;
import com.lcu.utils.EmailUtil;
import jakarta.annotation.Resource;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
//处理跨域的注解
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /* redis模版引擎 */
    @Autowired
    StringRedisTemplate redisTemplate;

    /* 谷歌验证码模版 */
    @Autowired
    Producer producer;

    /* 邮件发送工具类 */
    @Resource
    EmailUtil emailUtil;

    /* 发送验证码的接口 */
    @GetMapping("/getCode")
    public JsonResponse getCode(String email){
        //获取验证码内容
        String text = producer.createText();
        //将验证码内容存储到redis缓存数据库中
        // 为了下次能够对比用户输入的验证码时是否正确
        //2131ADASda-asdas2123-aDs
        String end = UUID.randomUUID().toString().replaceAll("-", "");
        String key = "code:"+ end;
        redisTemplate.opsForValue().set(key,text);

        //将验证那发送给指定邮箱用户
        emailUtil.sendMessageToEmail(text,email);

        return Result.success(key);
    }
    /* 登录接口 */
    @GetMapping("/login")
    public JsonResponse login(String key,String code,String email){
        //根据key值取出缓存中的验证码
        if(StringUtil.isNullOrEmpty(key)){
            return Result.error(Result.LOGIN_ERROR.code,Result.LOGIN_ERROR.msg, "请先点击获取验证再重试！");
        }
        String oldCode = redisTemplate.opsForValue().get(key);
        //判断两个验证码是否一致
        if(!oldCode.equals(code)){
            return Result.error(Result.LOGIN_ERROR.code,Result.LOGIN_ERROR.msg, "验证码错误");
        }
        //拿着邮箱账号查询用户信息
        User user =  userService.getUserInfo(email);
        //密码置空
        user.setPassword(null);
        return Result.success(user);
    }

    /* 账号密码登录 */
    @GetMapping("/passwordLogin")
    public JsonResponse passwordLogin(String username,String password){
        User user =  userService.passwordLogin(username,password);
        if(user != null)return Result.success(user);
        return Result.error();
    }
}