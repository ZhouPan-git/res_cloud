package com.yc.resfoods.configs;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zp
 * @Date 2023/8/25 21:16
 * @PackageName:com.yc.resfoods.configs
 * @ClassName: CustomerExceptionHandler
 * @Description:
 * @Version 1.0
 */
@ControllerAdvice
@Order(-10000)
public class CustomerExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<String,Object> handleRuntimeException(RuntimeException exception){
        Map<String, Object> map=new HashMap<>();
        map.put( "code" ,0);
        map.put( "msg" , " runtimeException occured" );
        return map;
    }
}