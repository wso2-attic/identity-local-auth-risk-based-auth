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
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Test RiskScore Service Util class
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({})

public class RiskScoreServiceUtilTest {
    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new PowerMockObjectFactory();
    }

    @Test
    public void testFileLoad() throws RiskScoreServiceConfigurationException {
//        PowerMockito.spy(RiskScoreServiceUtil.class);
//        ClassLoader loader = Test.class.getClassLoader();
//
        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource(Constants.IS_ANALYTICS_CONFIG_XML).getPath();
//        given(RiskScoreServiceUtil.getFilePath()).willReturn(path);
//
//        BufferedInputStream inputStream = null;
//        mockStatic(XMLInputFactory.class);
//        XMLInputFactory xmlInputFactory = mock(XMLInputFactory.class);
//        when(XMLInputFactory.newInstance()).thenReturn(xmlInputFactory);
//
//        when(XMLInputFactory.newInstance()).thenReturn(XMLInputFactory);
//        try {
//            inputStream = new BufferedInputStream(new FileInputStream(new File(path)));
//
//            XMLStreamReader parser = XMLInputFactory.newInstance().
//
//                    createXMLStreamReader(inputStream);
//            ServerConfiguration serverConfiguration = RiskScoreServiceUtil.loadServerConfig();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (XMLStreamException e) {
//            e.printStackTrace();
//        }
        RiskScoreServiceUtil.loadServerConfig();
    }
}

