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

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.adaptive.authentication.riskscore.util.ConnectionHandler;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * TODO: Class level comments
 */
public class GetRiskScoreFunctionImplTest {

    @Test
    public void testGetRiskScore() {
        GetRiskScoreFunctionImpl mockFunctionImpl = mock(GetRiskScoreFunctionImpl.class);
        JsAuthenticationContext jsContext = mock(JsAuthenticationContext.class);
        AuthenticationContext context = mock(AuthenticationContext.class);
        AuthenticatedUser user = mock(AuthenticatedUser.class);
        ConnectionHandler handler = mock(ConnectionHandler.class);
//        when(jsContext.getWrapped()).thenReturn(context);
//        when(context.getSubject()).thenReturn(user);
//        when(user.getUserName()).thenReturn("aofhbnf");
//        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
//        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
//        String timestamp = "1513580856472";
//        mockFunctionImpl.getRiskScore(jsContext, timestamp);
//
//        Mockito.verify(handler, Mockito.times(1)).calculateRiskScore(Matchers.any(AuthenticationContext.class),
//                Matchers.anyString(), Matchers.anyString());
    }
}
