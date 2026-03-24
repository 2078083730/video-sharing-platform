package com.lcu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* @Data==>getter和setter 方法
* @AllArgsConstructor =>全参构造方法
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse {
    private int code;
    private String msg;
    private Object data;

}
