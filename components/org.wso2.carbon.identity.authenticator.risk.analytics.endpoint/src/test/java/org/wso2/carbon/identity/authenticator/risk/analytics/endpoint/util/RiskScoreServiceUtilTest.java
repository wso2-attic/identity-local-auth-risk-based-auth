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
package org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.util;


import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.IObjectFactory;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.EventPublisher;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.ServerConfiguration;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.exception.RiskScoreServiceConfigurationException;
import org.wso2.carbon.utils.ServerConstants;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Test RiskScore Service Util class
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RiskScoreServiceUtil.class, System.class})

public class RiskScoreServiceUtilTest {
    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new PowerMockObjectFactory();
    }
    @Test
    public void testFileLoad() throws RiskScoreServiceConfigurationException {
        mockStatic(RiskScoreServiceUtil.class);
        when(RiskScoreServiceUtil.getFilePath()).thenReturn("/is-analytics-config.xml");

        ServerConfiguration serverConfiguration = RiskScoreServiceUtil.loadServerConfig();


    }
}

