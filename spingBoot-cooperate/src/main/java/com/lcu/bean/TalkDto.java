package com.lcu.bean;

import lombok.Data;

import java.util.List;

@Data
public class TalkDto extends Talk{
    private String username;
    private String pic;
    private List<ReplyDto> replyDtoList;
}
