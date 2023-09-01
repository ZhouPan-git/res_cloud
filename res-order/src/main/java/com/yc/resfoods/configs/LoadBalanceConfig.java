package com.yc.resfoods.configs;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author zp
 * @Date 2023/8/22 19:48
 * @PackageName:com.yc.resfoods.configs
 * @ClassName: LoadBalanceConfig
 * @Description:
 * @Version 1.0
 */
@LoadBalancerClients(
        value = {
                @LoadBalancerClient(value = "res-food",configuration=RoundRobinConfig.class),
                @LoadBalancerClient(value = "res-security",configuration=RoundRobinConfig.class)
        },defaultConfiguration = LoadBalancerClientConfiguration.class
)
public class LoadBalanceConfig {
    @LoadBalanced   //开启restTemplate对象的负载均衡功能
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}