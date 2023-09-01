package com.yc.resfoods.configs;

import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @Author zp
 * @Date 2023/8/22 19:59
 * @PackageName:com.yc.resfoods.configs
 * @ClassName: RoundRobinConfig
 * @Description:
 * @Version 1.0
 */
@Configuration
public class RoundRobinConfig {
        @Bean
        public RoundRobinLoadBalancer roundRobinReactorServiceLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {

            String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);

            // 创建一个RoundRobinLoadBalancer实例，使用懒加载的ServiceInstanceListSupplier
            return new RoundRobinLoadBalancer( loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
        }
}