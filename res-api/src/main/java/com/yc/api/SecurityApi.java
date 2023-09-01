package com.yc.api;

import com.yc.bean.Resorder;
import com.yc.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

/**
 * @Author zp
 * @Date 2023/8/23 14:11
 * @PackageName:com.yc.api
 * @ClassName: SecurityApi
 * @Description:
 * @Version 1.0
 */
@FeignClient(value = "res-security",path = "resfood",configuration = FeignConfig.class)
public interface SecurityApi {
    @PostMapping("hello")
    public Map<String,Object> orderFood(Resorder resorder, @RequestHeader("Authorization") String bearerToken);
}
