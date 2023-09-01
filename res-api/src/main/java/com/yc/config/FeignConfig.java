package com.yc.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author zp
 * @Date 2023/8/23 13:45
 * @PackageName:com.yc.config
 * @ClassName: FeignConfig
 * @Description:
 * @Version 1.0
 */
@Configuration
public class FeignConfig {
    //设置feign的网络请求的日志级别
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    @Component
    public class CustomerRequestInterceptor implements RequestInterceptor{
        @Override
        public void apply(RequestTemplate requestTemplate){
            requestTemplate.header("source","order-source");
        }
    }
}