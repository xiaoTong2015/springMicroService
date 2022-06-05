package com.thoughtmechanix.licenses.controller;

import com.thoughtmechanix.licenses.entity.Organization;
import com.thoughtmechanix.licenses.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/v1/organizations/{organizationId}/licenses")
@RestController
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value = "/{licensesId}/{clientType}", method = RequestMethod.GET)
    public Map<String, Object> getLicenses(@PathVariable("organizationId") String organizationId,
                                           @PathVariable("licensesId") String licensesId,
                                           @PathVariable("clientType") String clientType) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("organizationId", organizationId);
        resultMap.put("licensesId", licensesId);
        Organization organization = licenseService.retrieveOrgInfo(organizationId, clientType);
        resultMap.put("organization", organization);
        return resultMap;
    }


    @RequestMapping(value = "/test")
    public String test() {
        return "test license";
    }
}
