/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.identity.authenticator.risk.util;

import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.core.util.IdentityUtil;

import java.util.Map;

/**
 * Contains java object for risk score request
 */
public class RiskScoreRequestDTO {
    private String username;
    private String userStoreDomain;
    private String tenantDomain;
    private String remoteIp;
    private String timestamp;
    private String inboundAuthType;
    private String serviceProvider;
    private Boolean rememberMeEnabled;
    private Boolean forceAuthEnabled;
    private Boolean passiveAuthEnabled;
    private String identityProvider;
    private String stepAuthenticator;


    public RiskScoreRequestDTO() {
    }

    public RiskScoreRequestDTO(AuthenticationContext context) {
        this.username = context.getSubject().getUserName();
        this.userStoreDomain = context.getSubject().getUserStoreDomain();
        this.tenantDomain = context.getSubject().getTenantDomain();
        this.remoteIp = IdentityUtil.getClientIpAddress(context.getRequest());
        this.timestamp = String.valueOf(System.currentTimeMillis());
        this.identityProvider = context.getExternalIdP().getIdPName();
        this.serviceProvider = context.getServiceProviderName();
        this.inboundAuthType = context.getRequestType();
        this.rememberMeEnabled = context.isRememberMe();
        this.forceAuthEnabled = context.isForceAuthenticate();
        this.passiveAuthEnabled = context.isPassiveAuthenticate();
        this.stepAuthenticator = context.getCurrentAuthenticator();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserStoreDomain() {
        return userStoreDomain;
    }

    public void setUserStoreDomain(String userStoreDomain) {
        this.userStoreDomain = userStoreDomain;
    }

    public String getTenantDomain() {
        return tenantDomain;
    }

    public void setTenantDomain(String tenantDomain) {
        this.tenantDomain = tenantDomain;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getInboundAuthType() {
        return inboundAuthType;
    }

    public void setInboundAuthType(String inboundAuthType) {
        this.inboundAuthType = inboundAuthType;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Boolean getRememberMeEnabled() {
        return rememberMeEnabled;
    }

    public void setRememberMeEnabled(Boolean rememberMeEnabled) {
        this.rememberMeEnabled = rememberMeEnabled;
    }

    public Boolean getForceAuthEnabled() {
        return forceAuthEnabled;
    }

    public void setForceAuthEnabled(Boolean forceAuthEnabled) {
        this.forceAuthEnabled = forceAuthEnabled;
    }

    public Boolean getPassiveAuthEnabled() {
        return passiveAuthEnabled;
    }

    public void setPassiveAuthEnabled(Boolean passiveAuthEnabled) {
        this.passiveAuthEnabled = passiveAuthEnabled;
    }

    public String getIdentityProvider() {
        return identityProvider;
    }

    public void setIdentityProvider(String identityProvider) {
        this.identityProvider = identityProvider;
    }

    public String getStepAuthenticator() {
        return stepAuthenticator;
    }

    public void setStepAuthenticator(String stepAuthenticator) {
        this.stepAuthenticator = stepAuthenticator;
    }
}