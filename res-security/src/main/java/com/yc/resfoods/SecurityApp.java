package com.yc.resfoods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author zp
 * @Date 2023/8/21 10:33
 * @PackageName:com.yc.resfoods
 * @ClassName: SecurityApp
 * @Description:
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.yc.resfoods.dao")
@EnableDiscoveryClient
@EnableSwagger2
@EnableOpenApi
public class SecurityApp {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApp.class, args);
    }
}