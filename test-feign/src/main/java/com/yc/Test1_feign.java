package com.yc;

import com.yc.api.GitHub;
import com.yc.bean.Contributor;
import feign.Feign;
import feign.gson.GsonDecoder;

import java.util.List;

/**
 * @Author zp
 * @Date 2023/8/23 15:39
 * @PackageName:com.yc
 * @ClassName: Test1
 * @Description:
 * @Version 1.0
 */
public class Test1_feign {
    public static void main(String[] args) {
        GitHub github = Feign.builder()
                .decoder(new GsonDecoder())//解码器,解析响应
                .target(GitHub.class, "https://api.github.com");//代理:目标类->代理对象
        List<Contributor> contributors = github.contributors("OpenFeign", "feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor);
        }
    }
}