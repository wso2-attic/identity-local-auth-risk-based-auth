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
package org.wso2.carbon.identity.authenticator.risk;

//import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;
import org.wso2.carbon.identity.authenticator.risk.exception.RiskScoreCalculationException;
import org.wso2.carbon.identity.authenticator.risk.model.RiskScoreRequestDTO;
import org.wso2.carbon.identity.core.util.IdentityUtil;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;


/**
 * TODO: Class level comments
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({GetRiskScoreFunctionImpl.class, RiskScoreRequestDTO.class, IdentityUtil.class})
//@PrepareForTest(GetRiskScoreFunctionImpl.class)
//@PrepareEverythingForTest
@PowerMockIgnore({"javax.net.ssl.*", "javax.security.*"})
public class GetRiskScoreFunctionImplTest {
    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }


    @Test
    public void testCalculateRiskScoreMethodInvocation() throws Exception {
//        GetRiskScoreFunctionImpl mockFunctionImpl = new GetRiskScoreFunctionImplWrapper();
        GetRiskScoreFunctionImpl mockFunctionImpl = new GetRiskScoreFunctionImpl();
        JsAuthenticationContext jsAuthenticationContext = mock(JsAuthenticationContext.class);
        Map<String, String> propertyMap = mock(Map.class);
//
//        mockStatic(IdentityUtil.class);
//        AuthenticationContext authenticationContext = mock(AuthenticationContext.class);
//        AuthenticatedUser authenticatedUser = mock(AuthenticatedUser.class);
//        when(authenticationContext.getSubject()).thenReturn(authenticatedUser);
//        when(authenticatedUser.getUserName()).thenReturn("pamoda");
//        when(authenticatedUser.getUserStoreDomain()).thenReturn("PRIMARY");
//        when(authenticatedUser.getTenantDomain()).thenReturn("carbon.super");
//        when(authenticationContext.getExternalIdP()).thenReturn(mock(ExternalIdPConfig.class));
//        when(authenticationContext.getExternalIdP().getIdPName()).thenReturn("anyIDP");
//        when(authenticationContext.getRequest()).thenReturn(mock(HttpServletRequest.class));
//        when(authenticationContext.getServiceProviderName()).thenReturn("ISPprovider");
//        when(authenticationContext.getRequestType()).thenReturn("type");
//        when(authenticationContext.isRememberMe()).thenReturn(false);
//        when(authenticationContext.isForceAuthenticate()).thenReturn(false);
//        when(authenticationContext.isPassiveAuthenticate()).thenReturn(false);
//        when(authenticationContext.getCurrentAuthenticator()).thenReturn("currentAuth");
//        when(IdentityUtil.getClientIpAddress(Mockito.any(HttpServletRequest.class))).thenReturn("127.8.9.9");
//        when(jsAuthenticationContext.getWrapped()).thenReturn(authenticationContext);
//
//        mockStatic(RiskScoreRequestDTO.class);
        ConnectionHandler handler = mock(ConnectionHandler.class);
        RiskScoreRequestDTO riskScoreRequestDTO = mock(RiskScoreRequestDTO.class);

        whenNew(RiskScoreRequestDTO.class).withAnyArguments().thenReturn(riskScoreRequestDTO);
        whenNew(ConnectionHandler.class).withNoArguments().thenReturn(handler);
        mockFunctionImpl.getRiskScore(jsAuthenticationContext, propertyMap);
        verify(handler, Mockito.times(1)).calculateRiskScore(riskScoreRequestDTO);
    }

    @Test
    public void testCalculateRiskScoreExceptions() throws Exception {
        ConnectionHandler handler = mock(ConnectionHandler.class);
        RiskScoreRequestDTO riskScoreRequestDTO = mock(RiskScoreRequestDTO.class);
        whenNew(RiskScoreRequestDTO.class).withAnyArguments().thenReturn(riskScoreRequestDTO);
        whenNew(ConnectionHandler.class).withNoArguments().thenReturn(handler);
        when(handler.calculateRiskScore(riskScoreRequestDTO)).thenThrow(new RiskScoreCalculationException());
        GetRiskScoreFunctionImpl mockFunctionImpl = new GetRiskScoreFunctionImpl();
        JsAuthenticationContext jsAuthenticationContext = mock(JsAuthenticationContext.class);
        Map<String, String> propertyMap = mock(Map.class);
        Assert.assertEquals(mockFunctionImpl.getRiskScore(jsAuthenticationContext, propertyMap), 2);
    }


}
