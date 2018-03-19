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
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;
import org.wso2.carbon.event.stream.core.EventStreamService;
import org.wso2.carbon.event.template.manager.core.TemplateManagerService;
import org.wso2.carbon.event.template.manager.core.exception.TemplateManagerException;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.AuthRequestDTO;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.RiskScoreDTO;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.exception.RiskScoreServiceConfigurationException;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.impl.CalculateApiServiceImpl;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.util.Constants;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.util.RiskScoreServiceUtil;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.util.ServiceValueHolder;

import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;


/**
 * Tests the calculate risk score API implementation
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CalculateApiServiceImpl.class, ServiceValueHolder.class, RiskScoreServiceUtil.class})
@PowerMockIgnore({"javax.ws.*"})

public class CalculateApiServiceImplTest {

    @Mock
    private EventStreamService eventStreamService;

    @Mock
    private TemplateManagerService templateManagerService;

    @Mock
    private ServerConfiguration serverConfiguration;

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

        ServiceValueHolder.getInstance().setEventStreamService(eventStreamService);
        ServiceValueHolder.getInstance().setTemplateManagerService(templateManagerService);
    }

    @Test
    public void testResultContainer() throws Exception {
        Collection configs = mock(Collection.class);
        when(templateManagerService.getConfigurations(Constants.TEMPLATE_MANAGER_DOMAIN_NAME)).thenReturn(configs);
        when(configs.size()).thenReturn(2);

        ResultContainer container = new ResultContainer();
        container.addResult(1);
        container.addResult(1);
        whenNew(ResultContainer.class).withNoArguments().thenReturn(container);

        when(RiskScoreServiceUtil.loadServerConfig()).thenReturn(serverConfiguration);
        when(serverConfiguration.getRiskScoreStream()).thenReturn("RiskScore");

        whenNew(EventPublisher.class).withArguments(serverConfiguration).thenReturn(eventPublisher);

        CalculateApiServiceImpl calculateApiService = new CalculateApiServiceImpl();
        RiskScoreDTO scoreDTO = (RiskScoreDTO) calculateApiService.calculateRiskScore(authRequestDTO).getEntity();
        Assert.assertEquals((int) scoreDTO.getScore(), 1);
        Assert.assertNotNull(scoreDTO.toString());
    }

    @Test
    public void testCalculateApiServiceImpl() throws Exception {
        ResultContainer resultContainer = mock(ResultContainer.class);

        when(RiskScoreServiceUtil.loadServerConfig()).thenReturn(serverConfiguration);
        when(serverConfiguration.getRiskScoreStream()).thenReturn("RiskScore");

        whenNew(ResultContainer.class).withNoArguments().thenReturn(resultContainer);
        whenNew(EventPublisher.class).withArguments(serverConfiguration).thenReturn(eventPublisher);

        CalculateApiServiceImpl calculateApiService = new CalculateApiServiceImpl();
        calculateApiService.calculateRiskScore(authRequestDTO);

        verify(resultContainer, times(1)).getRiskScoreDTO();
        verify(eventPublisher, times(1)).sendEvent(Matchers.any(AuthRequestDTO.class), Matchers.anyString());
    }

    @Test
    public void testLoadConfigurationFailures() throws RiskScoreServiceConfigurationException {
        when(RiskScoreServiceUtil.loadServerConfig()).thenThrow(new RiskScoreServiceConfigurationException("loading " +
                "configurations failed"));

        try {
            CalculateApiServiceImpl calculateApiService = new CalculateApiServiceImpl();
        } catch (RuntimeException e) {
            Assert.assertNotNull(e);
        }
    }

    @Test
    public void testCountRulesException() throws Exception {
        when(templateManagerService.getConfigurations(Constants.TEMPLATE_MANAGER_DOMAIN_NAME)).thenThrow(new
                TemplateManagerException());

        ResultContainer container = new ResultContainer();
        whenNew(ResultContainer.class).withNoArguments().thenReturn(container);

        when(RiskScoreServiceUtil.loadServerConfig()).thenReturn(serverConfiguration);
        when(serverConfiguration.getRiskScoreStream()).thenReturn("RiskScore");

        whenNew(EventPublisher.class).withArguments(serverConfiguration).thenReturn(eventPublisher);

        CalculateApiServiceImpl calculateApiService = new CalculateApiServiceImpl();
        RiskScoreDTO scoreDTO = (RiskScoreDTO) calculateApiService.calculateRiskScore(authRequestDTO).getEntity();
        Assert.assertEquals((int) scoreDTO.getScore(), 2);
        Assert.assertNotNull(scoreDTO.toString());
    }
}
