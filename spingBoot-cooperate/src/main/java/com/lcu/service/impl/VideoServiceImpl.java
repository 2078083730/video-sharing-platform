package com.lcu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcu.bean.*;
import com.lcu.mapper.VideoMapper;
import com.lcu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    VideoMapper videoMapper;

    @Autowired
    StringRedisTemplate redisTemplate;
    @Override
    public int addVideo(Video video) {
        return videoMapper.addVideo(video);
    }

    @Override
    public Page<VideoDto> getVideos(Integer pageNum,Integer pageSize,Video video,Integer userId) {
        PageHelper.startPage(pageNum,pageSize);
        List<VideoDto> dtoList = videoMapper.getVideos(video);
        for(VideoDto vv :dtoList){
            if(redisTemplate.opsForSet().isMember("videoShare:"+vv.getId(),userId + "")){
                vv.setConfirmLike(true);
            }
            vv.setLikeCount(redisTemplate.opsForSet().size("videoShare:"+vv.getId()));
        }
        PageInfo<VideoDto> pageInfo = new PageInfo<>(dtoList);
        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getList(),pageInfo.getTotal());
    }

    @Override
    public List<Dict> getDict(String type) {
        return videoMapper.getDict(type);
    }

    @Override
    public int addDanmu(DanMu danMu) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date(System.currentTimeMillis()));
        danMu.setTime(time);
        return videoMapper.addDanmu(danMu);
    }

    @Override
    public List<DanMuDto> getDanmuList(Integer videoId) {

        return videoMapper.getDanmuList(videoId);
    }

    @Override
    public List<TalkDto> selectAllTalkAndReply(Talk talk) {
        return videoMapper.selectAllTalkAndReply(talk);
    }

    @Override
    public int addTalk(Talk talk) {
        return videoMapper.addTalk(talk);
    }

    @Override
    public int addReply(Reply reply) {
        return videoMapper.addReply(reply);
    }

    @Override
    public Map<String, Object> like(Integer userId, Integer videoId) {
        Map<String,Object> map = new HashMap<>();
        String likeTypeId = "videoShare:"+videoId;
        if(redisTemplate.opsForSet().isMember(likeTypeId,userId + "")){
            redisTemplate.opsForSet().remove(likeTypeId,userId + "");
            map.put("confirmLike",false);
            map.put("likeCount",this.likeCount(likeTypeId));
            return map;
        }else {
            redisTemplate.opsForSet().add(likeTypeId,userId.toString());
            map.put("confirmLike",true);
            map.put("likeCount",this.likeCount(likeTypeId));
            return map;
        }
    }
    public long likeCount(String likeTypeId){
        return redisTemplate.opsForSet().size(likeTypeId);
    }

    @Override
    public Page<VideoDto> getVideoList(Integer pageNum,Integer pageSize,Video video) {
        PageHelper.startPage(pageNum,pageSize);
        List<VideoDto> dtoList = videoMapper.getVideoList(video);

        PageInfo<VideoDto> pageInfo = new PageInfo<>(dtoList);
        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getList(),pageInfo.getTotal());
    }

    @Override
    public int changeVideoStatus(Integer status, Integer videoId) {
        return videoMapper.changeVideoStatus(status,videoId);
    }
}
