package com.lcu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/* springBoot启动类 核心注解 */
@SpringBootApplication
@MapperScan("com.lcu.mapper")
public class App {
    public static void main( String[] args ){
        SpringApplication.run(App.class);
    }
}
