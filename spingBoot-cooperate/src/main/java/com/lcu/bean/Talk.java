package com.lcu.bean;

import lombok.Data;

@Data
public class Talk {
    private int     id;
    private Integer videoId;
    private Integer userId;
    private String  talkContent;
    private String  publishTime;
    private String  hrefInfo;

}
