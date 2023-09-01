package com.yc.resfoods.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zp
 * @Date 2023/8/24 9:26
 * @PackageName:com.yc.resfoods.controller
 * @ClassName: ServiceConfig
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("service")
@RefreshScope
public class ServiceController {
    //利用DI将属性 res.pattern.dateformat 注入
    @Value("${res.pattern.dateformat}")
    private String dateFormat ;

    @RequestMapping("nowTime")
    public Map<String,Object> nowTime(){
        Map<String,Object> map=new HashMap<String,Object>();
        Date d=new Date();
        DateFormat df=new SimpleDateFormat(dateFormat);
        map.put("code",1);
        map.put("obj",df.format(d));
        return map;
    }
}