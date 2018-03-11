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
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.authenticator.risk.exception.RiskScoreCalculationException;
import org.wso2.carbon.identity.authenticator.risk.model.RiskScoreDTO;
import org.wso2.carbon.identity.authenticator.risk.model.RiskScoreRequestDTO;
import org.wso2.carbon.identity.authenticator.risk.model.RiskScoreRequestDTOTest;
import org.wso2.carbon.identity.authenticator.risk.util.RiskScoreConstants;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;


/**
 * Tests the connection with the IS Analytics
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConnectionHandler.class, HttpClientBuilder.class, EntityUtils.class})
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
        connectionHandler = new ConnectionHandler(mockHttpClient, mockHttpPost);
//        connectionHandler = new ConnectionHandler();
        StringEntity requestBody = mock(StringEntity.class);

//        mockStatic(HttpClientBuilder.class);
//
//        HttpClientBuilder mockBuilder = mock(HttpClientBuilder.class);
//
//        when(HttpClientBuilder.create()).thenReturn(mockBuilder);
//        when(mockBuilder.build()).thenReturn(mockHttpClient);
        when(mapper.writeValueAsString(riskScoreRequestDTO)).thenReturn("request");

        whenNew(ObjectMapper.class).withNoArguments().thenReturn(mapper);
        whenNew(StringEntity.class).withAnyArguments().thenReturn(requestBody);
        whenNew(HttpPost.class).withArguments(RiskScoreConstants.URL).thenReturn(mockHttpPost);

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
        RiskScoreDTO riskScore = mock(RiskScoreDTO.class);
//        connectionHandler = new ConnectionHandler(mockHttpClient,mockHttpPost);

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
            Assert.assertEquals(e.getMessage(), "Failed to get risk score from response. ");
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
            Assert.assertEquals(e.getMessage(), "Failed to connect with the server. ");
        }
    }

    @Test
    public void testConnectionError() throws Exception {
        log.info("Testing connection failure");
        whenNew(HttpPost.class).withArguments(RiskScoreConstants.URL).thenReturn(mockHttpPost);

        when(mockHttpClient.execute(Matchers.any(HttpPost.class))).thenThrow(new ConnectException());
        try {
            connectionHandler.calculateRiskScore(riskScoreRequestDTO);
            Assert.fail("Risk Calculation failed");
        } catch (RiskScoreCalculationException e) {
            Assert.assertEquals(e.getMessage(), "Failed to connect with the server. ");
        }
    }

//    @Test
//    public void testCilentBuilder() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//        mockStatic(HttpClientBuilder.class);
//
//        HttpClientBuilder mockBuilder = mock(HttpClientBuilder.class);
//
//        when(HttpClientBuilder.create()).thenReturn(mockBuilder);
//        when(mockBuilder.setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
//                .loadTrustMaterial(null,new TrustSelfSignedStrategy()).build()))).thenThrow(new KeyStoreException());
//
//    }


//    //auth request which satisfies all the 3 rules
//    @Test
//    public void testCalculateRiskScore1() {
//        log.info("Sending an authentication request satisfying 3 risk score rules ");
//        connectionHandler = new ConnectionHandler();
//        AuthenticationContext context = mock(AuthenticationContext.class);
//        AuthenticatedUser user = mock(AuthenticatedUser.class);
//        when(context.getSubject()).thenReturn(user);
//        when(user.getUserName()).thenReturn("aofhbnf");
//        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
//        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
//        timestamp = "1513580856472";
//        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp, "202.176.254.62"), 0);
//    }
//
//    // normal random request ( violates geolocation
//    @Test
//    public void testCalculateRiskScore2() {
//        log.info("Sending an authentication request violating geolocation ");
//        connectionHandler = new ConnectionHandler();
//        AuthenticationContext context = mock(AuthenticationContext.class);
//        AuthenticatedUser user = mock(AuthenticatedUser.class);
//        when(context.getSubject()).thenReturn(user);
//        when(user.getUserName()).thenReturn("pamoda");
//        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
//        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
//        timestamp = String.valueOf(System.currentTimeMillis());
//        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp, "203.43.1.43"), 1);
//    }
//
//    //violates ip range
//    @Test
//    public void testCalculateRiskScore3() {
//        log.info("Sending an authentication request violating ip range ");
//        connectionHandler = new ConnectionHandler();
//        AuthenticationContext context = mock(AuthenticationContext.class);
//        AuthenticatedUser user = mock(AuthenticatedUser.class);
//        when(context.getSubject()).thenReturn(user);
//        when(user.getUserName()).thenReturn("aofhbnf");
//        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
//        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
//        timestamp = "1513580856472";
//        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp, "12.176.254.62"), 1);
//    }
//
//
//    //violates time range
//    @Test
//    public void testCalculateRiskScore4() {
//        log.info("Sending an authentication request violating time range ");
//        connectionHandler = new ConnectionHandler();
//        AuthenticationContext context = mock(AuthenticationContext.class);
//        AuthenticatedUser user = mock(AuthenticatedUser.class);
//        when(context.getSubject()).thenReturn(user);
//        when(user.getUserName()).thenReturn("aofhbnf");
//        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
//        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
//        timestamp = "1514844324000";
//        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp, "202.176.254.62"), 1);
//    }
//
//
//    // violates all the 3 rules
//    @Test
//    public void testCalculateRiskScore5() {
//        log.info("Sending an authentication request violating 3 risk score rules ");
//        connectionHandler = new ConnectionHandler();
//        AuthenticationContext context = mock(AuthenticationContext.class);
//        AuthenticatedUser user = mock(AuthenticatedUser.class);
//        when(context.getSubject()).thenReturn(user);
//        when(user.getUserName()).thenReturn("aofhbnf");
//        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
//        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
//        timestamp = "1519855524000";
//        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp, "02.176.254.62"), 3);
//    }


}
