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
package org.wso2.carbon.identity.authenticator.risk.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.application.authentication.framework.config.model.ExternalIdPConfig;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.core.util.IdentityUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Test Risk Score Request object functions
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RiskScoreRequestDTO.class, IdentityUtil.class, EntityUtils.class})
public class RiskScoreRequestDTOTest {

    @Mock
    private AuthenticationContext authenticationContext;

    @Mock
    private Map<String, String> propertyMap;

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new PowerMockObjectFactory();
    }


    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockStatic(IdentityUtil.class);

        AuthenticatedUser authenticatedUser = mock(AuthenticatedUser.class);
        ExternalIdPConfig externalIdPConfig = mock(ExternalIdPConfig.class);
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);

        when(authenticationContext.getSubject()).thenReturn(authenticatedUser);
        when(authenticatedUser.getUserName()).thenReturn("user");
        when(authenticatedUser.getTenantDomain()).thenReturn("carbon.super");
        when(authenticatedUser.getUserStoreDomain()).thenReturn("PRIMARY");
        when(authenticationContext.getExternalIdP()).thenReturn(externalIdPConfig);
        when(externalIdPConfig.getIdPName()).thenReturn("local");
        when(authenticationContext.getRequest()).thenReturn(httpRequest);
        when(IdentityUtil.getClientIpAddress(httpRequest)).thenReturn("123.43.21.4");
        when(authenticationContext.getServiceProviderName()).thenReturn("travelocity");
        when(authenticationContext.getRequestType()).thenReturn("step");
        when(authenticationContext.isRememberMe()).thenReturn(false);
        when(authenticationContext.isForceAuthenticate()).thenReturn(false);
        when(authenticationContext.isPassiveAuthenticate()).thenReturn(false);
        when(authenticationContext.getCurrentAuthenticator()).thenReturn("step");
    }


    @Test
    public void testGettersAndSetters() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RiskScoreRequestDTO riskScoreRequestDTO = new RiskScoreRequestDTO(authenticationContext, propertyMap);

        StringEntity requestBody = null;
        RiskScoreRequestDTO newRequest = null;
        try {
            String requestBodyInString = mapper.writeValueAsString(riskScoreRequestDTO);
            requestBody = new StringEntity(requestBodyInString);
            String requestString = EntityUtils.toString(requestBody);
            newRequest = mapper.readValue(requestString, RiskScoreRequestDTO.class);

        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(requestBody);
        Assert.assertNotNull(newRequest);
    }
}
