package com.yc.resfoods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.swing.*;

/**
 * @Author zp
 * @Date 2023/8/22 11:37
 * @PackageName:com.yc.resfoods
 * @ClassName: OrderApp
 * @Description:
 * @Version 1.0
 */
@MapperScan("com.yc.resfoods.dao")
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.yc.api"})
public class OrderApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class, args);
    }
}