package com.yc.api;

import com.yc.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author zp
 * @Date 2023/8/23 10:32
 * @PackageName:com.yc
 * @ClassName: ResfoodApi
 * @Description:
 * @Version 1.0
 */
@FeignClient(value = "res-food",path = "resfood",configuration = FeignConfig.class)
public interface ResfoodApi {
    @GetMapping("findById/{fid}")
    public Map<String, Object> findById(@PathVariable Integer fid);

    @GetMapping("findAll")
    public Map<String, Object> findAll();

    @RequestMapping(value ="findByPage", method = {RequestMethod.GET})
    public Map<String,Object> findByPage(@RequestParam int pageno, @RequestParam int pagesize,
                                         @RequestParam String sortby, @RequestParam String sort);

}