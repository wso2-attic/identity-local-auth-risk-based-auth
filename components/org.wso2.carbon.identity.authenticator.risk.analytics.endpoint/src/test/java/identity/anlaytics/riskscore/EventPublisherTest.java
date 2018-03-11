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
package identity.anlaytics.riskscore;

import org.junit.runner.RunWith;
import org.mockito.Matchers;
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
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.EventPublisher;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.ServerConfiguration;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.AuthRequestDTO;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.exception.RiskScoreServiceConfigurationException;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * TODO: Class level comments
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({EventPublisher.class})
public class EventPublisherTest {

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new PowerMockObjectFactory();
    }

    @BeforeMethod
    void setUp() {
    }

    @Test
    public void testEventPubliser() throws Exception {
        ServerConfiguration serverConfiguration = mock(ServerConfiguration.class);
        when(serverConfiguration.getHostname()).thenReturn("localhost");
        when(serverConfiguration.getBinaryTCPPort()).thenReturn("9612");
        when(serverConfiguration.getBinarySSLPort()).thenReturn("9712");
        when(serverConfiguration.getUsername()).thenReturn("admin");
        when(serverConfiguration.getPassword()).thenReturn("admin");

        DataPublisher dataPublisher = mock(DataPublisher.class);
        whenNew(DataPublisher.class).withAnyArguments().thenReturn(dataPublisher);
        EventPublisher eventPublisher = new EventPublisher(serverConfiguration);
        AuthRequestDTO authRequestDTO = mock(AuthRequestDTO.class);
        when(authRequestDTO.getTimestamp()).thenReturn(String.valueOf(System.currentTimeMillis()));
        String id = String.valueOf(UUID.randomUUID());

        eventPublisher.sendEvent(authRequestDTO, id);

        Mockito.verify(dataPublisher, Mockito.times(1)).publish(Matchers.any(Event.class));
    }

    @Test
    public void testConstructor() throws Exception {
        ServerConfiguration serverConfiguration = mock(ServerConfiguration.class);
        when(serverConfiguration.getHostname()).thenReturn("localhost");
        when(serverConfiguration.getBinaryTCPPort()).thenReturn("9612");
        when(serverConfiguration.getBinarySSLPort()).thenReturn("9712");
        when(serverConfiguration.getUsername()).thenReturn("admin");
        when(serverConfiguration.getPassword()).thenReturn("admin");
        DataPublisher dataPublisher = mock(DataPublisher.class);
        whenNew(DataPublisher.class).withAnyArguments().thenThrow(new DataEndpointAgentConfigurationException
                ("DataEndpointAgentConfigurationException"));

        AuthRequestDTO authRequestDTO = mock(AuthRequestDTO.class);
        when(authRequestDTO.getTimestamp()).thenReturn(String.valueOf(System.currentTimeMillis()));
        try {
            EventPublisher eventPublisher = new EventPublisher(serverConfiguration);
            Assert.fail("");
        }catch (RiskScoreServiceConfigurationException ignored){

        }
        Mockito.verify(dataPublisher, Mockito.times(0)).publish(Matchers.any(Event.class));




    }
}
