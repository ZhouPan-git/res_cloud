package com.yc;

import com.sun.net.httpserver.Authenticator;
import com.yc.api.GitHub2_javax;
import com.yc.bean.Contributor;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.gson.GsonDecoder;
import feign.jaxrs.JAXRSContract;

import java.util.List;

/**
 * @Author zp
 * @Date 2023/8/23 15:55
 * @PackageName:com.yc.bean
 * @ClassName: Test2_javax
 * @Description:
 * @Version 1.0
 */
public class Test2_javax {
    public static void main(String[] args) {
        GitHub2_javax gitHub2= Feign.builder()
                .decoder(new GsonDecoder())
                .options(new Request.Options(1000,3500))//options方法指定连接超时时长
                .retryer(new Retryer.Default(5000,5000,3))//retryer主要指定重试策略
                //指定哪种注解规范
                .contract(new JAXRSContract())
                //为构造器配置本地的代理接口，远程的根目录，代理接口类的每一个接口方法的@RequestLine声明的值最终都会加上
                .target(GitHub2_javax.class,"https://api.github.com");
        List<Contributor> contributors=gitHub2.contributors("OpenFeign","feign");
        for (Contributor contributor : contributors){
            System.out.println(contributor);
        }
    }
}