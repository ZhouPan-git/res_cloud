package com.yc.resfoods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author zp
 * @Date 2023/8/20 15:54
 * @PackageName:com.yc.resfoods
 * @ClassName: ResfoodApplication
 * @Description:
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.yc.resfoods.dao")
@EnableDiscoveryClient
@EnableSwagger2
@EnableOpenApi
//@EnableNacosConfig
public class ResfoodApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResfoodApplication.class, args);
    }
}