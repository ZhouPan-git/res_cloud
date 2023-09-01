package com.yc.api;

import com.yc.bean.Contributor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * @Author zp
 * @Date 2023/8/23 15:49
 * @PackageName:com.yc.api
 * @ClassName: GitHub2_javax
 * @Description:
 * @Version 1.0
 */
@Produces("application/json")
public interface GitHub2_javax {
    @GET
    @Path("/repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@PathParam("owner") String owner,@PathParam("repo") String repo);

}