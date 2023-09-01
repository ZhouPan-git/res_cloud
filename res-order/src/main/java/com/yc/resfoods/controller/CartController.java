package com.yc.resfoods.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yc.api.ResfoodApi;
import com.yc.bean.Resfood;
import com.yc.resfoods.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author zp
 * @Date 2023/8/22 11:41
 * @PackageName:com.yc.resfoods.controller
 * @ClassName: CartController
 * @Description:
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ResfoodApi resfoodApi;

    @RequestMapping(value = "clearAll",method = RequestMethod.GET)
    public Map<String,Object> clearAll(@RequestHeader("Authorization") String bearerToken) {
        Map<String,Object> result = new HashMap<String,Object>();
        if (this.redisTemplate.hasKey("cart_"+bearerToken)){
            Set<Object> keys=this.redisTemplate.opsForHash().keys("cart_"+bearerToken);
            this.redisTemplate.opsForHash().delete("cart_"+bearerToken,keys.toArray());
            result.put("code",1);
            result.put("obj",keys);
        }else {
            result.put("code",0);
        }
        return result;
    }

    @RequestMapping(value = "addCart",method = RequestMethod.GET)
    public Map<String,Object> addCart(
            @RequestParam Integer fid,
            @RequestParam Integer num,
            @RequestHeader("Authorization") String bearerToken
    ){
        Map<String,Object> result = new HashMap<String,Object>();
        Resfood rf=null;
        //方案一：利用url地址直接访问服务
//        String url="http://localhost:9001/resfood/findById/"+fid;
        //方案二：利用服务名通过服务发现功能自动找到url
//        String url="http://res-food/resfood/findById/"+fid;
        //方案三：openFeign方案
        Map<String,Object> resultMap=resfoodApi.findById(fid);
//        Map<String,Object> resultMap=this.restTemplate.getForObject(url,Map.class);
        if ("1".equalsIgnoreCase(resultMap.get("code").toString())){
            Map m= (Map) resultMap.get("obj");
            ObjectMapper mapper=new ObjectMapper();
            rf=mapper.convertValue(m,Resfood.class);
        }else {
            result.put("code",0);
            result.put("msg","查无此商品"+fid);
            return result;
        }
        CartItem ci= (CartItem) this.redisTemplate.opsForHash().get("cart_"+bearerToken,fid+"");
        if (ci==null){
            ci=new CartItem();
            ci.setFood(rf);
            ci.setNum(num);
        }else {
            int newNum=ci.getNum()+num;
            ci.setNum(newNum);
        }
        if (ci.getNum()<=0){
            this.redisTemplate.opsForHash().delete("cart_"+bearerToken,fid+"");
        }else {
            ci.getSmallCount();
            this.redisTemplate.opsForHash().put("cart_"+bearerToken,fid+"",ci);
        }
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        result.put("code",1);
        Map m=redisTemplate.opsForHash().entries("cart_"+bearerToken);
        result.put("data",m.values());
        return result;
    }

    @RequestMapping(value = "getCartInfo",method = RequestMethod.GET)
    public Map<String,Object> getCartInfo(@RequestHeader("Authorization") String bearerToken){
        Map<String,Object> result = new HashMap<String,Object>();
        if (this.redisTemplate.hasKey("cart_"+bearerToken)){
            Map<Object, Object> cart=this.redisTemplate.opsForHash().entries("cart_"+bearerToken);
            log.info("token为："+bearerToken+",的购物车为："+cart);
            result.put("code",1);
            result.put("data",cart.values());
        }else {
            result.put("code",0);
        }
        return result;
    }
}