package com.yc.api;

import com.yc.bean.Contributor;
import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * @Author zp
 * @Date 2023/8/23 15:36
 * @PackageName:com.yc.api
 * @ClassName: Github
 * @Description:
 * @Version 1.0
 */
public interface GitHub {
    //传入的参数后可以拼接成url: https://api.github.com/repos/OpenFeign/feign/contributors
    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@Param( "owner") String owner,@Param("repo") String repo);

}