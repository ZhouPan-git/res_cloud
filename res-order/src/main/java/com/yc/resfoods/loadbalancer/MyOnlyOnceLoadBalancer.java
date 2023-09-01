package com.yc.resfoods.loadbalancer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Author zp
 * @Date 2023/8/22 20:35
 * @PackageName:com.yc.resfoods.loadbalancer
 * @ClassName: MyOnlyOnceLoadBalancer
 * @Description:
 * @Version 1.0
 */
@Slf4j
public class MyOnlyOnceLoadBalancer implements ReactorServiceInstanceLoadBalancer {
        private  ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

        public MyOnlyOnceLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
            this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        }

        @Override
        public Mono<Response<ServiceInstance>> choose(Request request) {
            ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);

            return supplier.get(request)
                    .next()
                    .map(servicelnstances -> processInstanceResponse(supplier, servicelnstances));
        }

        private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
                                                                  List<ServiceInstance> instances) {
            Response<ServiceInstance> instanceResponse = getInstanceResponse(instances);

            if (supplier instanceof SelectedInstanceCallback && instanceResponse.hasServer()) {
                ((SelectedInstanceCallback) supplier).selectedServiceInstance(instanceResponse.getServer());
            }

            return instanceResponse;
        }

        private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
            log.info("自定义负载");
            if (instances.isEmpty()) {
                return new EmptyResponse();
            }

            // 固定访问第一个服务
            ServiceInstance instance = instances.get(0);
            return new DefaultResponse(instance);
        }
}