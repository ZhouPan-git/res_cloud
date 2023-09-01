package com.yc;

import com.yc.api.GitHub2_javax;
import com.yc.bean.Contributor;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.jaxrs.JAXRSContract;
import feign.okhttp.OkHttpClient;

import java.util.List;

/**
 * @Author zp
 * @Date 2023/8/23 16:05
 * @PackageName:com.yc
 * @ClassName: Test3_feign_okhttp
 * @Description:
 * @Version 1.0
 */
public class Test3_feign_okhttp {
    public static void main(String[] args) {
        GitHub2_javax github = Feign.builder()
                        .client(new OkHttpClient())//TODO:修改了客户端
                        .decoder(new GsonDecoder())
                        .contract(new JAXRSContract())
                        .target(GitHub2_javax.class,"https://api.github.com");
        List<Contributor> contributors = github.contributors("OpenFeign","feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor);
        }

    }
}