package com.lcu.controller;

import com.lcu.bean.*;
import com.lcu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    VideoService videoService;

    @PostMapping("/addVideo")
    public JsonResponse addVideo(@RequestBody Video video){
        int i =  videoService.addVideo(video);
        if(i>0) return Result.success();
        return Result.error();
    }

    @GetMapping("/getVideos")
    public JsonResponse getVideos(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                  @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                  Video video,Integer userId){
         Page<VideoDto> videos =  videoService.getVideos(pageNum,pageSize,video,userId);
         return Result.success(videos);
    }
    @GetMapping("/getDict")
    public JsonResponse getDict(String  type){
        List<Dict> videos =  videoService.getDict(type);
        return Result.success(videos);
    }

    @PostMapping("/addDanMu")
    public JsonResponse addDanMu(@RequestBody DanMu danMu){
       int i =  videoService.addDanmu(danMu);
       if(i > 0)return Result.success();
       return Result.error();
    }

    @GetMapping("/getComments")
    public JsonResponse getDict(Integer videoId){
        List<DanMuDto> danmuList =  videoService.getDanmuList(videoId);
        return Result.success(danmuList);
    }

    @GetMapping("/selectAllTalkAndReply")
    public JsonResponse selectAllTalkAndReply(Talk talk){
        List<TalkDto> talkDtos =  videoService.selectAllTalkAndReply(talk);
        return Result.success(talkDtos);
    }
    /* 添加评论 */
    @PostMapping("/addTalk")
    public JsonResponse addTalk(@RequestBody Talk talk){
        return Result.success(videoService.addTalk(talk));
    }
    /* 添加回复 */
    @PostMapping("/addReply")
    public JsonResponse addReply(@RequestBody Reply reply){
        return Result.success(videoService.addReply(reply));
    }

    @GetMapping("/addLike")
    public JsonResponse addLike(Integer userId,Integer videoId){
        Map<String,Object> map  =  videoService.like(userId,videoId);
        return Result.success(map);
    }


    @GetMapping("/getVideoList")
    public JsonResponse getVideoList(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                  @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                  Video video){
        Page<VideoDto> videos =  videoService.getVideoList(pageNum,pageSize,video);
        return Result.success(videos);
    }

    @GetMapping("/changeVideoStatus")
    public JsonResponse changeVideoStatus(Integer status,Integer videoId){
        int i =  videoService.changeVideoStatus(status,videoId);
        if(i > 0)return Result.success();
        return Result.error();
    }
}
