package com.yc.api;

import com.yc.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

/**
 * @Author zp
 * @Date 2023/8/23 13:46
 * @PackageName:com.yc.api
 * @ClassName: CartApi
 * @Description:4
 * @Version 1.0
 */
@FeignClient(value = "res-order",path = "cart",configuration = FeignConfig.class)
//@Produces("application/json")
public interface CartApi {
    @RequestMapping(value = "clearAll",method = RequestMethod.GET)
//    @GET
//    @Path("clearAll")
    public Map<String,Object> clearAll(@RequestHeader("Authorization") String bearerToken);

    @RequestMapping(value = "addCart",method = RequestMethod.GET)
    public Map<String,Object> addCart(
            @RequestParam Integer fid,
            @RequestParam Integer num,
            @RequestHeader("Authorization") String bearerToken
    );

    @RequestMapping(value = "getCartInfo",method = RequestMethod.GET)
    public Map<String,Object> getCartInfo(@RequestHeader("Authorization") String bearerToken);
}