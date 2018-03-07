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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.wso2.carbon.databridge.agent.DataPublisher;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.EventPublisher;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.ServerConfiguration;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.AuthRequestDTO;

import java.util.UUID;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * TODO: Class level comments
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({EventPublisher.class, DataPublisher.class})
public class EventPublisherTest {
    DataPublisher dataPublisher;

    @BeforeMethod
    void setUp() {
    }

    @Test
    public void testEventPubliser() {
        ServerConfiguration serverConfiguration = mock(ServerConfiguration.class);
        DataPublisher dataPublisher = mock(DataPublisher.class);
        EventPublisher eventPublisher = new EventPublisher(dataPublisher, serverConfiguration);

        AuthRequestDTO authRequestDTO = mock(AuthRequestDTO.class);
        when(authRequestDTO.getUsername()).thenReturn("pamoda");
        when(authRequestDTO.getUserStoreDomain()).thenReturn("PRIMARY");
        when(authRequestDTO.getTenantDomain()).thenReturn("carbon.super");
        when(authRequestDTO.getRemoteIp()).thenReturn("123.43.23.2");
        when(authRequestDTO.getTimestamp()).thenReturn(String.valueOf(System.currentTimeMillis()));
        String id = String.valueOf(UUID.randomUUID());

        eventPublisher.sendEvent(authRequestDTO, id);

        Mockito.verify(dataPublisher, Mockito.times(1)).publish(Matchers.any(Event.class));
    }
}
