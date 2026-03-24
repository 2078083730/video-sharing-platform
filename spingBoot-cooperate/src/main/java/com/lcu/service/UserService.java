package com.lcu.service;

import com.lcu.bean.User;

public interface UserService {
    User getUserInfo(String email);

    User passwordLogin(String username, String password);
}
