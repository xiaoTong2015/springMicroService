package com.thoughtmechanix.licenses.service;

import com.thoughtmechanix.licenses.entity.Organization;

import java.util.Map;

public interface LicenseService {

    Map<String, Object> getLicense(String organizationed, String licenseId, String clientType) throws Exception;

    Organization retrieveOrgInfo(String organizationId, String clientType) throws Exception;

}
