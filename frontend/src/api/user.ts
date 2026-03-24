import request from "@/util/request";

export const sendCode = (email:any)=>request({
    //服务器接口地址
    url:"/user/getCode",
    params:{email}
})

/* 登录请求 */
export const login = (key:any,code:any,email:any)=>request({
    //服务器接口地址
    url:"/user/login",
    params:{key,code,email}
})
/* 密码登录请求 */
export const passwordLogin = (username:any,password:any)=>request({
    //服务器接口地址
    url:"/user/passwordLogin",
    params:{username,password}
})
/* chatAi聊天接口 */
export const chatAi = (message:any)=>request({
    //服务器接口地址
    url:"/chat/ai/generate",
    params:{message}
})