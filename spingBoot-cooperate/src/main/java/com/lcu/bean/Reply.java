package com.lcu.bean;

import lombok.Data;

@Data
public class Reply {
    private int     id;
    private Integer talkId;
    private Integer userId;
    private String  replyContent;
    private String  replyTime;
    private String  rname;
    private String  replyHref;
}
