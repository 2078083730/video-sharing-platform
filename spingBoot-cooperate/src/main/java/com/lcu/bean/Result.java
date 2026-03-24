package com.lcu.bean;

public enum Result {
    SUCCESS(200,"ok"),
    ERROR(5001,"系统错误"),
    LOGIN_ERROR(5002,"登录异常");

    public int code;
    public String msg;
    private Result(int code ,String msg){
        this.code = code;
        this.msg = msg;
    }

    /* 封装公共的成功或失败的方法 */
    public static JsonResponse success(){
        return new JsonResponse(Result.SUCCESS.code,Result.SUCCESS.msg,null);
    }
    public static JsonResponse success(Object data){
        return new JsonResponse(Result.SUCCESS.code,Result.SUCCESS.msg,data);
    }
    public static JsonResponse error(){
        return new JsonResponse(Result.ERROR.code,Result.ERROR.msg,null);
    }
    /* 自定义的错误方法 */
    public static JsonResponse error(int code,String msg,String data){
        return new JsonResponse(code,msg,data);
    }

}
