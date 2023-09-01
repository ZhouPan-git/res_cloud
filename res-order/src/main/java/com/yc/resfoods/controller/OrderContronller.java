package com.yc.resfoods.controller;


import com.yc.api.SecurityApi;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.resfoods.biz.ResorderBiz;
import com.yc.resfoods.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @Author zp
 * @Date 2023/8/22 11:41
 * @PackageName:com.yc.resfoods.controller
 * @ClassName: OrderContronller
 * @Description:
 * @Version 1.0
 */
@RequestMapping(value= "order")
@RestController
@Slf4j
public class OrderContronller {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ResorderBiz resorderBiz;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SecurityApi securityApi;

    @PostMapping("orderFood")
    public Map<String,Object> orderFood(Resorder resorder, @RequestHeader("Authorization") String bearerToken){
        Map<String,Object> map = new HashMap<String,Object>();
        if (!this.redisTemplate.hasKey("cart_"+bearerToken)||this.redisTemplate.opsForHash().entries("cart_"+bearerToken).size()<=0) {
            map.put("code",0);
            return map;
        }
        Map<String, CartItem> cart=this.redisTemplate.opsForHash().entries("cart_"+bearerToken);
        Collection<CartItem> cis=cart.values();

        Resuser user=new Resuser();
        //方案一：利用url地址直接访问服务
//        String url="http://localhost:8001/resfood/hello";
        //方案二：利用服务名通过服务发现功能自动找到url
        String url="http://res-security/ressecurity/resfood/hello";
        HttpHeaders header=new HttpHeaders(){
            {set("Authorization", bearerToken);}
        };
        ResponseEntity<Map> re=restTemplate.exchange(url, HttpMethod.GET,new HttpEntity<Map>(header),Map.class);
        Map m=re.getBody();
        int userid=Integer.parseInt(m.get("userid").toString());
        user.setUserid(userid);
        LocalDateTime now=LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        resorder.setOrdertime(formatter.format(now));
        LocalDateTime deliveryTime=now.plusHours(5);
        resorder.setOrdertime(formatter.format(deliveryTime));
        resorder.setStatus(0);
        resorderBiz.order(resorder,new HashSet(cis),user);
        double total=0;
        for (CartItem ci:cis){
            total+=ci.getSmallCount();
        }
        this.redisTemplate.delete("cart_"+bearerToken);
        map.put("code",1);
        return map;
    }

    @GetMapping("payAction")
    public Map<String,Object> payAction(Integer flag) throws InterruptedException {
        //测试慢请求
        if (flag==null){
            Thread.sleep(1000);
        }
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code",1);
        return map;
    }
}