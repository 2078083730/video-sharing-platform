import request from "@/util/request";

export const getVideos = (type:any,title:any,pageNum:any,pageSize:any,id:any,userId:any)=>request({
    //服务器接口地址
    url:"/video/getVideos",
    params:{type,title,pageNum,pageSize,id,userId}
})
export const getDict = (type:any)=>request({
    //服务器接口地址
    url:"/video/getDict",
    params:{type}
})

export const getVideoList = (title:any,pageNum:any,pageSize:any)=>request({
    //服务器接口地址
    url:"/video/getVideoList",
    params:{title,pageNum,pageSize}
})


export const postComments = (danmu:any)=>request({
    //服务器接口地址
    url:"/video/addDanMu",
    method:"POST",
    data:danmu
})

export const getComments = (videoId:any)=>request({
    //服务器接口地址
    url:"/video/getComments",
    params:{videoId}
})


export const selectAllTalkAndReply = (videoId:any)=>request({
    //服务器接口地址
    url:"/video/selectAllTalkAndReply",
    params:{videoId}
})

export const addTalk = (talkForm:any)=>request({
    //服务器接口地址
    url:"/video/addTalk",
   method:'POST',
    data:talkForm
})
export const addReplyInfo = (replyForm:any)=>request({
    //服务器接口地址
    url:"/video/addReply",
    method:'POST',
    data:replyForm
})

export const addLikeInfo = (userId:any,videoId:any)=>request({
    //服务器接口地址
    url:"/video/addLike",
    method:'GET',
    params:{userId,videoId}
})



export const changeVideoStatus = (status:any,videoId:any)=>request({
    //服务器接口地址
    url:"/video/changeVideoStatus",
    method:'GET',
    params:{status,videoId}
})