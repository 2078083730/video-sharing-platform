package com.lcu.bean;

import lombok.Data;

@Data
public class VideoDto extends Video{
    private String languageName;
    private String username;
    private String avatar;
    private Boolean confirmLike;
    private Long likeCount;
}
