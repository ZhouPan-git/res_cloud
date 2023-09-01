package com.yc.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @Author zp
 * @Date 2023/8/29 18:58
 * @PackageName:com.yc.filters
 * @ClassName: MyTimeGatewayFilterFactory
 * @Description: 自定义的记录资源处理时间的过滤器
 * @Version 1.0
 */
@Component
@Slf4j
public class MyTimeGatewayFilterFactory extends AbstractGatewayFilterFactory<MyTimeGatewayFilterFactory.MyTimeUnitConfig> {
    public MyTimeGatewayFilterFactory() {
        super(MyTimeUnitConfig.class);
    }

    @Override
    public GatewayFilter apply(MyTimeUnitConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                String name = exchange.getRequest().getQueryParams().getFirst("name");
                log.info("请求来了，参数为：" + config.getName() + "，请求资源为：" + exchange.getRequest().getPath());
                long start = System.currentTimeMillis();
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    long end = System.currentTimeMillis();
                    long diff = end - start;
                    if ("分".equals(config.getName())) {
                        log.info("执行时间为：" + (diff / 60 / 1000) + "分");
                    } else if ("秒".equals(config.getName())) {
                        log.info("执行时间为：" + (diff / 1000) + "秒");
                    }
                }));
            }
        };
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("name");
    }

    public static class MyTimeUnitConfig {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}