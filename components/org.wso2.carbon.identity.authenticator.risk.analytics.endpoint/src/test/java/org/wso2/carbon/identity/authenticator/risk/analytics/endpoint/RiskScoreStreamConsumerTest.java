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
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.event.template.manager.core.TemplateManagerService;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.util.ServiceValueHolder;

import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Tests RiskScoreConsumer class
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ServiceValueHolder.class})
public class RiskScoreStreamConsumerTest {

    @Mock
    private Map<String, ResultContainer> resultContainerMap;

    @Mock
    private ResultContainer container;

    @Mock
    private Event event;

    @Mock
    private TemplateManagerService templateManagerService;

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new PowerMockObjectFactory();
    }

    @BeforeMethod
    void setUp() {
        initMocks(this);
        ServiceValueHolder.getInstance().setTemplateManagerService(templateManagerService);
        ServiceValueHolder.getInstance().setResultContainerMap(resultContainerMap);
    }

    @Test
    public void testEventAdd() {
        RiskScoreStreamConsumer consumer = new RiskScoreStreamConsumer("RiskScoreStream");
        Object[] payloadData = new Object[2];
        payloadData[0] = "someUUID";
        payloadData[1] = 1;

        when(event.getPayloadData()).thenReturn(payloadData);
        when(resultContainerMap.get(payloadData[0])).thenReturn(container);
        consumer.onEvent(event);
        verify(container, times(1)).addResult(1);
    }

    @Test
    public void testGetStreamId() {
        RiskScoreStreamConsumer consumer = new RiskScoreStreamConsumer("RiskScoreStream");
        Assert.assertEquals(consumer.getStreamId(), "RiskScoreStream");
    }
}
