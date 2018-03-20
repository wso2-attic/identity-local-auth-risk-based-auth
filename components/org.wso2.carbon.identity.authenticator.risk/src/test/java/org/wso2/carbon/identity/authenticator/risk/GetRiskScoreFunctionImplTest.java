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

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;
import org.wso2.carbon.identity.authenticator.risk.exception.RiskScoreCalculationException;
import org.wso2.carbon.identity.authenticator.risk.model.RiskScoreRequestDTO;
import org.wso2.carbon.identity.core.util.IdentityUtil;

import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Tests the Function Implementation class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({GetRiskScoreFunctionImpl.class, IdentityUtil.class})
@PowerMockIgnore({"javax.net.ssl.*", "javax.security.*"})
public class GetRiskScoreFunctionImplTest {
    @Mock
    private RiskScoreRequestDTO riskScoreRequestDTO;
    @Mock
    private ConnectionHandler handler;
    @Mock
    private JsAuthenticationContext jsAuthenticationContext;
    @Mock
    private Map<String, String> propertyMap;

    private GetRiskScoreFunctionImpl mockFunctionImpl;

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new PowerMockObjectFactory();
    }

    @BeforeMethod
    public void setUp() throws Exception {
        initMocks(this);
        mockFunctionImpl = new GetRiskScoreFunctionImpl();
        mockStatic(IdentityUtil.class);
        PowerMockito.when(IdentityUtil.class, "getProperty", "Analytics.Enabled").thenReturn("true");
    }

    @Test
    public void testCalculateRiskScoreMethodInvocation() throws Exception {
        whenNew(RiskScoreRequestDTO.class).withAnyArguments().thenReturn(riskScoreRequestDTO);
        whenNew(ConnectionHandler.class).withNoArguments().thenReturn(handler);
        mockFunctionImpl.getRiskScore(jsAuthenticationContext, propertyMap);
        verify(handler, Mockito.times(1)).calculateRiskScore(riskScoreRequestDTO);
    }

    @Test
    public void testCalculateRiskScoreExecutionException() throws Exception {
        whenNew(RiskScoreRequestDTO.class).withAnyArguments().thenReturn(riskScoreRequestDTO);
        whenNew(ConnectionHandler.class).withNoArguments().thenReturn(handler);
        when(handler.calculateRiskScore(riskScoreRequestDTO)).thenThrow(new RiskScoreCalculationException());
        Assert.assertEquals(mockFunctionImpl.getRiskScore(jsAuthenticationContext, propertyMap), 2);
    }

    @Test
    public void testCalculateRiskScoreConstructorException() throws Exception {
        whenNew(RiskScoreRequestDTO.class).withAnyArguments().thenReturn(riskScoreRequestDTO);
        whenNew(ConnectionHandler.class).withNoArguments().thenThrow(new RiskScoreCalculationException());
        Assert.assertEquals(mockFunctionImpl.getRiskScore(jsAuthenticationContext, propertyMap), 2);
        verify(handler, Mockito.times(0)).calculateRiskScore(riskScoreRequestDTO);
    }
}
