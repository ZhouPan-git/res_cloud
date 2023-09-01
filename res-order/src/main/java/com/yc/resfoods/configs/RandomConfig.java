package com.yc.resfoods.configs;

import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @Author zp
 * @Date 2023/8/22 19:59
 * @PackageName:com.yc.resfoods.configs
 * @ClassName: RandomConfig
 * @Description:
 * @Version 1.0
 */
@Configuration
public class RandomConfig {
        @Bean
        public ReactorServiceInstanceLoadBalancer randomReactorServiceInstanceLoadBalancer(
                Environment environment,
                LoadBalancerClientFactory loadBalancerClientFactory) {
            String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
            return new RandomLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
        }
}