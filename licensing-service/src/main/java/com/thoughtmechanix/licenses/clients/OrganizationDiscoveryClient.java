package com.thoughtmechanix.licenses.clients;

import com.thoughtmechanix.licenses.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrganizationDiscoveryClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    public Organization getOrganization(String organizationId){
        RestTemplate restTemplate = new RestTemplate();
        // 获取organization服务的所有实例列表
        List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");
        if (instances.size() == 0){
            return null;
        }
        // 检索要调用的服务端点
        String serviceUri = String.format("%s/v1/organizations/%s",
                instances.get(0).getUri().toString(), organizationId);
        // 使用restTemplate调用端点
        ResponseEntity<Organization> restExchange = restTemplate
                .exchange(serviceUri, HttpMethod.GET, null, Organization.class, organizationId);
        return restExchange.getBody();
    }
}
