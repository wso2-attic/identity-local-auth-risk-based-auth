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

package org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;

@ApiModel(description = "")
public class AuthRequestDTO {

    @NotNull
    private String username = null;

    @NotNull
    private String userStoreDomain = null;

    @NotNull
    private String tenantDomain = null;

    @NotNull
    private String remoteIp = null;

    @NotNull
    private String timestamp = null;

    @NotNull
    private String inboundAuthType = null;

    @NotNull
    private String serviceProvider = null;

    @NotNull
    private Boolean rememberMeEnabled = null;

    @NotNull
    private Boolean forceAuthEnabled = null;

    @NotNull
    private Boolean passiveAuthEnabled = null;

    @NotNull
    private String identityProvider = null;

    @NotNull
    private String stepAuthenticator = null;

    private Map<String, String> propertyMap = new HashMap<String, String>();

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("userStoreDomain")
    public String getUserStoreDomain() {
        return userStoreDomain;
    }

    public void setUserStoreDomain(String userStoreDomain) {
        this.userStoreDomain = userStoreDomain;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("tenantDomain")
    public String getTenantDomain() {
        return tenantDomain;
    }

    public void setTenantDomain(String tenantDomain) {
        this.tenantDomain = tenantDomain;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("remoteIp")
    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("inboundAuthType")
    public String getInboundAuthType() {
        return inboundAuthType;
    }

    public void setInboundAuthType(String inboundAuthType) {
        this.inboundAuthType = inboundAuthType;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("serviceProvider")
    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("rememberMeEnabled")
    public Boolean getRememberMeEnabled() {
        return rememberMeEnabled;
    }

    public void setRememberMeEnabled(Boolean rememberMeEnabled) {
        this.rememberMeEnabled = rememberMeEnabled;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("forceAuthEnabled")
    public Boolean getForceAuthEnabled() {
        return forceAuthEnabled;
    }

    public void setForceAuthEnabled(Boolean forceAuthEnabled) {
        this.forceAuthEnabled = forceAuthEnabled;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("passiveAuthEnabled")
    public Boolean getPassiveAuthEnabled() {
        return passiveAuthEnabled;
    }

    public void setPassiveAuthEnabled(Boolean passiveAuthEnabled) {
        this.passiveAuthEnabled = passiveAuthEnabled;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("identityProvider")
    public String getIdentityProvider() {
        return identityProvider;
    }

    public void setIdentityProvider(String identityProvider) {
        this.identityProvider = identityProvider;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("stepAuthenticator")
    public String getStepAuthenticator() {
        return stepAuthenticator;
    }

    public void setStepAuthenticator(String stepAuthenticator) {
        this.stepAuthenticator = stepAuthenticator;
    }

    /**
     **/
    @ApiModelProperty(value = "")
    @JsonProperty("propertyMap")
    public Map<String, String> getPropertyMap() {
        return propertyMap;
    }

    public void setPropertyMap(Map<String, String> propertyMap) {
        this.propertyMap = propertyMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuthRequestDTO {\n");

        sb.append("  username: ").append(username).append("\n");
        sb.append("  userStoreDomain: ").append(userStoreDomain).append("\n");
        sb.append("  tenantDomain: ").append(tenantDomain).append("\n");
        sb.append("  remoteIp: ").append(remoteIp).append("\n");
        sb.append("  timestamp: ").append(timestamp).append("\n");
        sb.append("  inboundAuthType: ").append(inboundAuthType).append("\n");
        sb.append("  serviceProvider: ").append(serviceProvider).append("\n");
        sb.append("  rememberMeEnabled: ").append(rememberMeEnabled).append("\n");
        sb.append("  forceAuthEnabled: ").append(forceAuthEnabled).append("\n");
        sb.append("  passiveAuthEnabled: ").append(passiveAuthEnabled).append("\n");
        sb.append("  identityProvider: ").append(identityProvider).append("\n");
        sb.append("  stepAuthenticator: ").append(stepAuthenticator).append("\n");
        sb.append("  propertyMap: ").append(propertyMap).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
