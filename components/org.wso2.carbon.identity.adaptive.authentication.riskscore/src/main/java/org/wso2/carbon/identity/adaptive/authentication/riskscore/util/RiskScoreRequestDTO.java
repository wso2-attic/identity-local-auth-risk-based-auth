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
package org.wso2.carbon.identity.adaptive.authentication.riskscore.util;

import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.core.util.IdentityUtil;

/**
 * Contains java object for risk score request
 */
public class RiskScoreRequestDTO {
    private String username;
    private String userStoreDomain;
    private String tenantDomain;
    private String remoteIp;
    private String timestamp;

    public RiskScoreRequestDTO(String username, String userStoreDomain, String tenantDomain, String remoteIp, String
            timestamp) {
        this.username = username;
        this.userStoreDomain = userStoreDomain;
        this.tenantDomain = tenantDomain;
        this.remoteIp = remoteIp;
        this.timestamp = timestamp;
    }

    public RiskScoreRequestDTO(AuthenticationContext context) {
        this.username = context.getSubject().getUserName();
        this.userStoreDomain = context.getSubject().getUserStoreDomain();
        this.tenantDomain = context.getSubject().getTenantDomain();
        this.remoteIp = IdentityUtil.getClientIpAddress(context.getRequest());
        this.timestamp = String.valueOf(System.currentTimeMillis());

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
}
