package com.yc.resfoods.configs;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author zp
 * @Date 2023/8/25 18:39
 * @PackageName:com.yc.resfoods.configs
 * @ClassName: CustomerRequestOriginParser
 * @Description:
 * @Version 1.0
 */
@Component
@Slf4j
public class CustomerRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        log.info("解析请求头中的origin");
        String source=httpServletRequest.getHeader("source");
        log.info("请求来源为："+source);
        return source;
    }
}