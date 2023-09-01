package com.yc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author zp
 * @Date 2023/8/30 14:51
 * @PackageName:com.yc.controller
 * @ClassName: IndexContronller
 * @Description:
 * @Version 1.0
 */
@Controller
public class IndexContronller {
    @RequestMapping("/")
    public String GoToIndex(){
        System.out.println("访问首页地址:");
        return "redirect:/index.html";
    }
}