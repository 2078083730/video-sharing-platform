package com.lcu.service;

import com.lcu.bean.*;

import java.util.List;
import java.util.Map;

public interface VideoService {
    int addVideo(Video video);
   Page<VideoDto> getVideos(Integer pageNum,Integer pageSize,Video video,Integer userId);
    List<Dict> getDict(String type);

    int addDanmu(DanMu danMu);
    List<DanMuDto> getDanmuList(Integer videoId);

    List<TalkDto> selectAllTalkAndReply(Talk talk);

    int addTalk(Talk talk);

    int addReply(Reply reply);

    Map<String, Object> like(Integer userId, Integer videoId);

    Page<VideoDto> getVideoList(Integer pageNum, Integer pageSize, Video video);

    int changeVideoStatus(Integer status, Integer videoId);
}
