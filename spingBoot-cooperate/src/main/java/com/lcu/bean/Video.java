package com.lcu.bean;

import lombok.Data;

@Data
public class Video {
    private int id;
    private String title;
    private Integer type;
    private String pic;
    private String basePath;
    private Integer uid;
    private Integer status;

}
