package com.lcu.bean;

import lombok.Data;
/* 生成getter和setter方法 */
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Integer role;
}
