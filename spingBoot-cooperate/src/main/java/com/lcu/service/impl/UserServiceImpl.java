package com.lcu.service.impl;

import com.lcu.bean.User;
import com.lcu.mapper.UserMapper;
import com.lcu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserInfo(String email) {
        return userMapper.getUserInfo(email);
    }

    @Override
    public User passwordLogin(String username, String password) {
        return userMapper.passwordLogin(username,password);
    }
}
