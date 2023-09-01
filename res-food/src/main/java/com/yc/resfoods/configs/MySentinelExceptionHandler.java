package com.yc.resfoods.configs;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zp
 * @Date 2023/8/25 21:21
 * @PackageName:com.yc.resfoods.configs
 * @ClassName: MySentinelExceptionHandler
 * @Description:
 * @Version 1.0
 */
@Component
public class MySentinelExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws Exception {
        String msg = null;
        if (ex instanceof FlowException) {
            msg = "访问频繁，请稍候再试";
        } else if (ex instanceof DegradeException) {
            msg = "系统降级";
        } else if (ex instanceof ParamFlowException) {
            msg = "热点参数异常：" + ex.getMessage() + "" + ((ParamFlowException) ex).getResourceName() + "，" + ex.getRule();
            // ParamFlowException异常需要额外的依赖包
            // <dependency>
            //     <groupId>com.alibaba.csp</groupId>
            //     <artifactId>sentinel-parameter-flow-control</artifactId>
            // </dependency>
        } else if (ex instanceof SystemBlockException) {
            msg = "系统规则限流或降级";
        } else if (ex instanceof AuthorityException) {
            msg = "授权规则不通过";
        } else {
            msg = "未知限流降级";
        }
        // 设置 HTTP 状态码为 200
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        // 这里可以对 msg 进行进一步处理，例如返回给前端
        response.setHeader("Content-Type","application/json;charset=utf-8");
        response.setContentType( "application/json;charset=utf-8");
        Map map=new HashMap();
        map.put( "code",0);
        map. put( "msg", msg);
        ObjectMapper om=new ObjectMapper();
        String json=om.writeValueAsString(map );
        PrintWriter writer=response.getWriter();
        writer.write(json);
        writer.flush();
    }
}