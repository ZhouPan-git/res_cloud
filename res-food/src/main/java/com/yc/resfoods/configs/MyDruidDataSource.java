package com.yc.resfoods.configs;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


/**
 * @Author zp
 * @Date 2023/8/24 10:28
 * @PackageName:com.yc.resfoods.configs
 * @ClassName: MyDruidDataSource
 * @Description:
 * @Version 1.0
 */
@Configuration
@Slf4j
@RefreshScope
public class MyDruidDataSource {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    @Primary
    @RefreshScope
    public DataSource druid(){
        log.info("编程式的数据源创建");
        DruidDataSource ds=new DruidDataSource();
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        return ds;
    }
}