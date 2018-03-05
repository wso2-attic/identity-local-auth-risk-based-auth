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
package org.wso2.carbon.identity.adaptive.authentication.riskscore;

import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.adaptive.authentication.riskscore.util.ConnectionHandler;
import org.wso2.carbon.identity.adaptive.authentication.riskscore.util.RiskScoreRequestDTO;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;


/**
 * TODO: Class level comments
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RiskScoreRequestDTO.class)
//@PrepareForTest(RiskScoreRequestDTO.class)
public class GetRiskScoreFunctionImplTest {

    @Test
    public void testGetRiskScore() throws Exception {
//        GetRiskScoreFunctionImpl mockFunctionImpl = new GetRiskScoreFunctionImpl();
//        RiskScoreRequestDTO riskScoreRequestDTO = mock(RiskScoreRequestDTO.class);
//        JsAuthenticationContext jsAuthenticationContext = mock(JsAuthenticationContext.class);
////        AuthenticationContext authenticationContext = mock(AuthenticationContext.class);
////        AuthenticatedUser authenticatedUser = mock(AuthenticatedUser.class);
////        when(authenticationContext.getSubject()).thenReturn(authenticatedUser);
////        when(authenticatedUser.getUserName()).thenReturn("pamoda");
////        when(authenticatedUser.getUserStoreDomain()).thenReturn("PRIMARY");
////        when(authenticatedUser.getTenantDomain()).thenReturn("carbon.super");
//
//        riskScoreRequestDTO.setUsername("pamoda");
//        riskScoreRequestDTO.setUserStoreDomain("PRIMARY");
//        riskScoreRequestDTO.setTenantDomain("carbon.super");
//        riskScoreRequestDTO.setRemoteIp("123.54.43.2");
//        riskScoreRequestDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));
//        ConnectionHandler handler = mock(ConnectionHandler.class);
//        whenNew(RiskScoreRequestDTO.class).withArguments(Matchers.any(AuthenticationContext.class)).thenReturn(riskScoreRequestDTO);
//
//        mockFunctionImpl.getRiskScore(jsAuthenticationContext);
//        Mockito.verify(handler, Mockito.times(1)).calculateRiskScore(riskScoreRequestDTO);
    }
}
