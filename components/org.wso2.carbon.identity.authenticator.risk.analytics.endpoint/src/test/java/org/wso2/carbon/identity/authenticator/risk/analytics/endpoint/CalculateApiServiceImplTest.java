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
package org.wso2.carbon.identity.authenticator.risk.analytics.endpoint;

import org.junit.runner.RunWith;
import org.mockito.Matchers;
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
import org.wso2.carbon.event.stream.core.EventStreamService;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.AuthRequestDTO;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.RiskScoreDTO;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.exception.RiskScoreServiceConfigurationException;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.impl.CalculateApiServiceImpl;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.util.CarbonServiceValueHolder;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.util.RiskScoreServiceUtil;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;


/**
 * TODO: Class level comments
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CalculateApiServiceImpl.class, CarbonServiceValueHolder.class, RiskScoreServiceUtil.class, Response
        .class})
public class CalculateApiServiceImplTest {

    @Mock
    private EventStreamService eventStreamService;

    @Mock
    private ServerConfiguration serverConfiguration;

    @Mock
    private RiskScoreDTO riskScoreDTO;

    @Mock
    private ResultContainer resultContainer;

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private AuthRequestDTO authRequestDTO;


    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new PowerMockObjectFactory();
    }

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockStatic(RiskScoreServiceUtil.class);
        mockStatic(CarbonServiceValueHolder.class);
        mockStatic(Response.class);

        CarbonServiceValueHolder carbonServiceValueHolder = mock(CarbonServiceValueHolder.class);
        when(CarbonServiceValueHolder.getInstance()).thenReturn(carbonServiceValueHolder);
        when(CarbonServiceValueHolder.getInstance().getEventStreamService()).thenReturn(eventStreamService);

    }


//    @Test
//    public void testGetRiskScoreApiService() throws Exception {
//        AuthRequestDTO mockAuthRequest = mock(AuthRequestDTO.class);
////        GetRiskScoreApiServiceImpl mockApiService =mock(GetRiskScoreApiServiceImpl.class);
////        whenNew(GetRiskScoreApiServiceImpl.class).withNoArguments().thenReturn(mockApiService);
//        when(mockAuthRequest.getUsername()).thenReturn("pamoda");
//        when(mockAuthRequest.getUserStoreDomain()).thenReturn("PRIMARY");
//        when(mockAuthRequest.getTenantDomain()).thenReturn("carbon.super");
//        when(mockAuthRequest.getRemoteIp()).thenReturn("230.10.10.23");
//        when(mockAuthRequest.getTimestamp()).thenReturn(String.valueOf(System.currentTimeMillis()));
////        GetRiskScoreApiService mockApiService = new GetRiskScoreApiServiceImpl();
////        Assert.assertEquals(mockApiService.getRiskScore(mockAuthRequest).getStatus(), 200);
//    }

    @Test
    public void testCalculateApiServiceImpl() throws Exception {

        when(RiskScoreServiceUtil.loadServerConfig()).thenReturn(serverConfiguration);
        when(serverConfiguration.getRiskScoreStream()).thenReturn("RiskScore");

        whenNew(ResultContainer.class).withNoArguments().thenReturn(resultContainer);
        whenNew(RiskScoreDTO.class).withNoArguments().thenReturn(riskScoreDTO);
        whenNew(EventPublisher.class).withArguments(serverConfiguration).thenReturn(eventPublisher);

        Response.ResponseBuilder builder = mock(Response.ResponseBuilder.class);
        Response response = mock(Response.class);
        when(Response.ok()).thenReturn(builder);
        when(builder.entity(Matchers.any(RiskScoreDTO.class))).thenReturn(builder);
        when(builder.build()).thenReturn(response);

        when(response.ok().build()).thenReturn(response);
        CalculateApiServiceImpl calculateApiService = new CalculateApiServiceImpl();
        calculateApiService.calculateRiskScore(authRequestDTO);

        verify(resultContainer, times(1)).getRiskScoreDTO();
        verify(eventPublisher, times(1)).sendEvent(Matchers.any(AuthRequestDTO.class), Matchers.anyString());
    }

    @Test
    public void testLoadConfigurationFailures() throws RiskScoreServiceConfigurationException {
        when(RiskScoreServiceUtil.loadServerConfig()).thenThrow(new RiskScoreServiceConfigurationException("loading configurations failed"));

        try{
            CalculateApiServiceImpl calculateApiService = new CalculateApiServiceImpl();
        } catch (RuntimeException e){
            Assert.assertNotNull(e);
        }
    }
}
