package com.lcu.mapper;

import com.lcu.bean.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper {
    int addVideo(Video video);
    List<VideoDto> getVideos(Video video);
    List<Dict> getDict(String type);

    int addDanmu(DanMu danMu);
    List<DanMuDto> getDanmuList(Integer videoId);

    List<TalkDto> selectAllTalkAndReply(Talk talk);

    int addTalk(Talk talk);

    int addReply(Reply reply);

    List<VideoDto> getVideoList(Video video);

    int changeVideoStatus(@Param("status") Integer status, @Param("videoId") Integer videoId);
}
