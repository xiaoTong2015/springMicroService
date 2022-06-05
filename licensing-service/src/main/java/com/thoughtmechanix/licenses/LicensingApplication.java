package com.thoughtmechanix.licenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
// @EnableDiscoveryClient 对Ribbon和Ribbon中缓存的注册服务的访问，查询通过Ribbon注册的所有服务以及这些服务对应的URL
// 使用 @EnableDiscoveryClient 注解后，由Spring框架管理的所有RestTemplate都将注入一个Ribbon拦截器
@EnableDiscoveryClient
// @EnableFeignClients 通过java接口和注解的方式映射Ribbon将要调用的基于Eureka的服务
// SpringCloud将动态生成一个代理类，用于调用目标的REST服务
@EnableFeignClients
// 启用Hystrix
@EnableCircuitBreaker
// 启动EurekaClient
@EnableEurekaClient
// @RefreshScope
public class LicensingApplication {

    // @LoadBalanced 注解告诉SpringCloud创建了一个支持Ribbon的RestTemplate类
    // @LoadBalanced 实现了客户端负载均衡
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(LicensingApplication.class, args);
    }
}
