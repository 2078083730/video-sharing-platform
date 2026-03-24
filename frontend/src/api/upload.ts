import request from "@/util/request";

export const addVideo = (form:any)=>request({
    //服务器接口地址
    url:"/video/addVideo",
    method:"POST",
    data:form
})