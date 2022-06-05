package com.thoughtmechanix.organization.controller;

import com.thoughtmechanix.organization.entity.Organization;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1/organizations")
public class OrganizationController {

    @RequestMapping(value="/{organizationId}",method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        Organization organization = new Organization();
        organization.setId(organizationId);
        return organization;
    }
}
