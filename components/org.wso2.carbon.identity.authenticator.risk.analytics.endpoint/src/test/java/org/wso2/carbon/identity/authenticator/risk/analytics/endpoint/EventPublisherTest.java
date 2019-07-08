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
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;
import org.wso2.carbon.databridge.agent.DataPublisher;
import org.wso2.carbon.databridge.agent.exception.DataEndpointAgentConfigurationException;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.AuthRequestDTO;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.exception.RiskScoreServiceConfigurationException;

import java.util.HashMap;
import java.util.UUID;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Test the Event Publisher Class.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({EventPublisher.class})
public class EventPublisherTest {

    private ServerConfiguration serverConfiguration;

    @Mock
    private DataPublisher dataPublisher;

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new PowerMockObjectFactory();
    }

    @BeforeMethod
    void setUp() {
        initMocks(this);
        serverConfiguration = new ServerConfiguration("localhost", "9612", "9712",
                "9444", "admin", "admin", "RiskScoreRequest", "RiskScorePerRule");
    }

    @Test
    public void testEventPubliser() throws Exception {
        whenNew(DataPublisher.class).withAnyArguments().thenReturn(dataPublisher);
        EventPublisher eventPublisher = new EventPublisher(serverConfiguration);
        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setUsername("admin");
        authRequestDTO.setUserStoreDomain("PRIMARY");
        authRequestDTO.setTenantDomain("carbon.super");
        authRequestDTO.setRemoteIp("123.43.32.5");
        authRequestDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));
        authRequestDTO.setInboundAuthType("step");
        authRequestDTO.setServiceProvider("travelocity");
        authRequestDTO.setRememberMeEnabled(false);
        authRequestDTO.setForceAuthEnabled(false);
        authRequestDTO.setPassiveAuthEnabled(false);
        authRequestDTO.setIdentityProvider("local");
        authRequestDTO.setStepAuthenticator("1");
        authRequestDTO.setPropertyMap(new HashMap<String, String>());

        String id = String.valueOf(UUID.randomUUID());

        eventPublisher.sendEvent(authRequestDTO, id);
        Assert.assertNotNull(authRequestDTO.toString());
        Mockito.verify(dataPublisher, Mockito.times(1)).publish(Matchers.any(Event.class));
    }

    @Test
    public void testConstructor() throws Exception {
        whenNew(DataPublisher.class).withAnyArguments().thenThrow(new DataEndpointAgentConfigurationException
                ("DataEndpointAgentConfigurationException"));
        try {
            EventPublisher eventPublisher = new EventPublisher(serverConfiguration);
        } catch (RiskScoreServiceConfigurationException e) {
            Assert.assertNotNull(e);
        }
        Mockito.verify(dataPublisher, Mockito.times(0)).publish(Matchers.any(Event.class));
    }
}
