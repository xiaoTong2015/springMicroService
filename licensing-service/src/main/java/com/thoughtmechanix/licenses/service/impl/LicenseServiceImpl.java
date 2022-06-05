package com.thoughtmechanix.licenses.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thoughtmechanix.licenses.clients.OrganizationDiscoveryClient;
import com.thoughtmechanix.licenses.clients.OrganizationFeignClient;
import com.thoughtmechanix.licenses.clients.OrganizationRestTemplateClient;
import com.thoughtmechanix.licenses.entity.License;
import com.thoughtmechanix.licenses.entity.Organization;
import com.thoughtmechanix.licenses.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LicenseServiceImpl implements LicenseService {

    @Autowired
    OrganizationFeignClient organizationFeignClient;

    @Autowired
    OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;

    @Override
    public Map<String, Object> getLicense(String organizationed, String licenseId, String clientType) throws Exception {
        return null;
    }

    @Override
    public Organization retrieveOrgInfo(String organizationId, String clientType) throws Exception {
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }
        return organization;
    }



    /**
     * @HystrixCommand 注解会使用Hystrix断路器包装 getLicensesByOrg 方法
     * 每当调用时间超过1000ms时，断路器将中断对 getLicensesByOrg 方法的调用
     * @param organizationId
     * @return
     */
    @HystrixCommand(commandProperties = {
            // execution.isolation.thread.timeoutInMilliseconds 用于设置断路器的超时时间（单位ms）
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "12000")
    },      // fallbackMethod 属性定义了类中的一个方法，如果调用失败则会调用该方法
    fallbackMethod = "buildFallbackLicenseList",
            //  threadPoolKey 属性定义线程池的唯一名称
    threadPoolKey = "licenseByOrgThreadPool",
            // threadPoolProperties 属性用于定义和定制threadPool的行为
    threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "30"),
            @HystrixProperty(name = "maxQueueSize", value = "10")
    })
    public List<License> getLicensesByOrg(String organizationId){
        List<License> licenses = new ArrayList<>();
        return licenses;
    }

    private List<License> buildFallbackLicenseList(String organizationId){
        List<License> fallbackList = new ArrayList<>();
        License license = new License();
        license.setComment("断路器默认数据");
        fallbackList.add(license);
        return fallbackList;
    }
}
