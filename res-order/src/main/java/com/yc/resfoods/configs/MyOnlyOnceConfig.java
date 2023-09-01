package com.yc.resfoods.configs;

import com.yc.resfoods.loadbalancer.MyOnlyOnceLoadBalancer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @Author zp
 * @Date 2023/8/22 20:50
 * @PackageName:com.yc.resfoods.configs
 * @ClassName: MyOnlyOnceConfig
 * @Description:
 * @Version 1.0
 */
@Configuration
public class MyOnlyOnceConfig {
        @Bean
        public MyOnlyOnceLoadBalancer myOnlyOneReactorServiceLoadBalancer(
                Environment environment,
                LoadBalancerClientFactory loadBalancerClientFactory) {

            String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);

            // 创建一个MyOnlyOneLoadBalancer实例，使用懒加载的ServiceInstanceListSupplier

            return new MyOnlyOnceLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class));
        }
}