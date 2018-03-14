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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
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
import org.wso2.carbon.base.ServerConfiguration;
import org.wso2.carbon.identity.authenticator.risk.exception.RiskScoreCalculationException;
import org.wso2.carbon.identity.authenticator.risk.model.RiskScoreDTO;
import org.wso2.carbon.identity.authenticator.risk.model.RiskScoreRequestDTO;
import org.wso2.carbon.identity.authenticator.risk.util.RiskScoreConstants;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.utils.CarbonUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.KeyStore;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;


/**
 * Tests the connection with the IS Analytics
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConnectionHandler.class, EntityUtils.class, HttpClientBuilder.class, CarbonUtils.class, IdentityUtil
        .class})
@PowerMockIgnore({"javax.net.ssl.*", "javax.security.*"})
public class ConnectionHandlerTest {
    private static final Log log = LogFactory.getLog(ConnectionHandler.class);

    private ConnectionHandler connectionHandler;

    @Mock
    private RiskScoreRequestDTO riskScoreRequestDTO;

    @Mock
    private CloseableHttpClient mockHttpClient;

    @Mock
    private CloseableHttpResponse mockHttpResponse;

    @Mock
    private HttpPost mockHttpPost;

    @Mock
    private StatusLine mockStatusLine;

    @Mock
    private ObjectMapper mapper;


    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new PowerMockObjectFactory();
    }


    @BeforeMethod
    public void setUp() throws Exception {
        initMocks(this);
        StringEntity requestBody = mock(StringEntity.class);
        mockStatic(HttpClientBuilder.class);
        mockStatic(CarbonUtils.class);
        mockStatic(KeyStore.class);
        mockStatic(IdentityUtil.class);
        PowerMockito.spy(HttpClientBuilder.class);
        HttpClientBuilder builder = mock(HttpClientBuilder.class);
        ServerConfiguration serverConfiguration = mock(ServerConfiguration.class);
        FileInputStream inputStream = mock(FileInputStream.class);

        PowerMockito.when(HttpClientBuilder.class, "create").thenReturn(builder);
        PowerMockito.when(CarbonUtils.class, "getServerConfiguration").thenReturn(serverConfiguration);
//        PowerMockito.when(IdentityUtil.class, "getProperty", "Analytics.ISAnalyticsServerURL").thenReturn
//                ("https://localhost:9444");

        when(builder.build()).thenReturn(mockHttpClient);
        when(mapper.writeValueAsString(riskScoreRequestDTO)).thenReturn("request");
        when(serverConfiguration.getFirstProperty("Security.KeyStore.Location")).thenReturn("path");
        when(serverConfiguration.getFirstProperty("Security.KeyStore.Password")).thenReturn("password");
        when(serverConfiguration.getFirstProperty("Security.KeyStore.Type")).thenReturn("JKS");

        whenNew(ObjectMapper.class).withNoArguments().thenReturn(mapper);
        whenNew(StringEntity.class).withAnyArguments().thenReturn(requestBody);
        whenNew(HttpPost.class).withAnyArguments().thenReturn(mockHttpPost);
        whenNew(FileInputStream.class).withAnyArguments().thenReturn((FileInputStream) inputStream);
        connectionHandler = new ConnectionHandler();

    }


    @Test
    public void testResponseCode() throws Exception {
        log.info("Testing response code");

        HttpEntity httpEntity = mock(HttpEntity.class);
        RiskScoreDTO riskScore = mock(RiskScoreDTO.class);
//        connectionHandler = new ConnectionHandler(mockHttpClient,mockHttpPost);

        when(mockHttpClient.execute(Matchers.any(HttpPost.class))).thenReturn(mockHttpResponse);
        when(mockStatusLine.getStatusCode()).thenReturn(200);
        when(mockHttpResponse.getStatusLine()).thenReturn(mockStatusLine);
        when(mockHttpResponse.getEntity()).thenReturn(httpEntity);
        mockStatic(EntityUtils.class);
        when(EntityUtils.toString(httpEntity)).thenReturn("response");
        when(mapper.readValue("response", RiskScoreDTO.class)).thenReturn(riskScore);
        when(riskScore.getScore()).thenReturn(1);
        Assert.assertEquals(connectionHandler.calculateRiskScore(riskScoreRequestDTO), 1);
    }

    @Test
    public void testResposeConvertionError() throws Exception {
        log.info("Testing response converting error");

        HttpEntity httpEntity = mock(HttpEntity.class);

        when(mockHttpClient.execute(Matchers.any(HttpPost.class))).thenReturn(mockHttpResponse);
        when(mockStatusLine.getStatusCode()).thenReturn(200);
        when(mockHttpResponse.getStatusLine()).thenReturn(mockStatusLine);
        when(mockHttpResponse.getEntity()).thenReturn(httpEntity);
        mockStatic(EntityUtils.class);
        when(EntityUtils.toString(httpEntity)).thenReturn("response");
        when(mapper.readValue("response", RiskScoreDTO.class)).thenThrow(new IOException());
        try {
            connectionHandler.calculateRiskScore(riskScoreRequestDTO);
        } catch (RiskScoreCalculationException e) {
            Assert.assertEquals(e.getMessage(), "Failed to get risk score from response");
        }
    }

    @Test
    public void testResponseError() throws Exception {
        log.info("Testing response error code");

        when(mockHttpClient.execute(Matchers.any(HttpPost.class))).thenReturn(mockHttpResponse);
        when(mockStatusLine.getStatusCode()).thenReturn(404);
        when(mockHttpResponse.getStatusLine()).thenReturn(mockStatusLine);
        try {
            connectionHandler.calculateRiskScore(riskScoreRequestDTO);
        } catch (RiskScoreCalculationException e) {
            Assert.assertEquals(e.getMessage(), "HTTP error code : " + mockHttpResponse.getStatusLine().getStatusCode
                    ());
        }
    }

    @Test
    public void testResponseDelay() throws Exception {
        log.info("Testing response delay");

        when(mockHttpClient.execute(Matchers.any(HttpPost.class))).thenThrow(new SocketTimeoutException());
        try {
            connectionHandler.calculateRiskScore(riskScoreRequestDTO);
        } catch (RiskScoreCalculationException e) {
            Assert.assertEquals(e.getMessage(), "Failed to connect with the server");
        }
    }

    @Test
    public void testConnectionError() throws Exception {
        log.info("Testing connection failure");
        whenNew(HttpPost.class).withArguments(RiskScoreConstants.RISK_SCORE_SERVICE_PATH).thenReturn(mockHttpPost);

        when(mockHttpClient.execute(Matchers.any(HttpPost.class))).thenThrow(new ConnectException());
        try {
            connectionHandler.calculateRiskScore(riskScoreRequestDTO);
            Assert.fail("Risk Calculation failed");
        } catch (RiskScoreCalculationException e) {
            Assert.assertEquals(e.getMessage(), "Failed to connect with the server");
        }
    }

}
