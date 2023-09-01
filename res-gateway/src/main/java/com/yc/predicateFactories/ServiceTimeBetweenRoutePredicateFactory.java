package com.yc.predicateFactories;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @Author zp
 * @Date 2023/8/29 16:34
 * @PackageName:com.yc.predicateFactories
 * @ClassName: ServiceTimeBetweenRoutePredicateFactory
 * @Description:
 * @Version 1.0
 */
@Slf4j
@Component
public class ServiceTimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<ServiceTimeBetweenRoutePredicateFactory.CustomTimeBetweenConfig> {
    @Override
    public Predicate<ServerWebExchange> apply(CustomTimeBetweenConfig config) {
        LocalTime startTime = config.getStartTime();
        LocalTime endTime = config.getEndTime();

        return serverWebExchange -> {
            LocalTime now = LocalTime.now();
            return now.isAfter(startTime) && now.isBefore(endTime);
        };
    }

    @Data
    public static class CustomTimeBetweenConfig {
        private LocalTime startTime;
        private LocalTime endTime;
    }

    public ServiceTimeBetweenRoutePredicateFactory() {
        super(CustomTimeBetweenConfig.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("startTime", "endTime");
    }
}
