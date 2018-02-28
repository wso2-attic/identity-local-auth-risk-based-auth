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
package org.wso2.carbon.identity.adaptive.authentication.riskscore;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.mockito.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.adaptive.authentication.riskscore.util.ConnectionHandler;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Tests the connection with the IS Analytics and the risk scores
 */
public class ConnectionHandlerTest {
    private static final Log log = LogFactory.getLog(ConnectionHandler.class);

    private ConnectionHandler connectionHandler;
    private String timestamp;

    @BeforeClass
    public void setup() {

    }

    //auth request which satisfies all the 3 rules
    @Test
    public void testCalculateRiskScore1() {
        log.info("Sending an authentication request satisfying 3 risk score rules ");
        connectionHandler = new ConnectionHandler();
        AuthenticationContext context = mock(AuthenticationContext.class);
        AuthenticatedUser user = mock(AuthenticatedUser.class);
        when(context.getSubject()).thenReturn(user);
        when(user.getUserName()).thenReturn("aofhbnf");
        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
        timestamp = "1513580856472";
        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp, "202.176.254.62"), 0);
    }

    // normal random request ( violates geolocation
    @Test
    public void testCalculateRiskScore2() {
        log.info("Sending an authentication request violating geolocation ");
        connectionHandler = new ConnectionHandler();
        AuthenticationContext context = mock(AuthenticationContext.class);
        AuthenticatedUser user = mock(AuthenticatedUser.class);
        when(context.getSubject()).thenReturn(user);
        when(user.getUserName()).thenReturn("pamoda");
        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
        timestamp = String.valueOf(System.currentTimeMillis());
        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp, "203.43.1.43"), 1);
    }

    //violates ip range
    @Test
    public void testCalculateRiskScore3() {
        log.info("Sending an authentication request violating ip range ");
        connectionHandler = new ConnectionHandler();
        AuthenticationContext context = mock(AuthenticationContext.class);
        AuthenticatedUser user = mock(AuthenticatedUser.class);
        when(context.getSubject()).thenReturn(user);
        when(user.getUserName()).thenReturn("aofhbnf");
        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
        timestamp = "1513580856472";
        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp, "12.176.254.62"), 1);
    }


    //violates time range
    @Test
    public void testCalculateRiskScore4() {
        log.info("Sending an authentication request violating time range ");
        connectionHandler = new ConnectionHandler();
        AuthenticationContext context = mock(AuthenticationContext.class);
        AuthenticatedUser user = mock(AuthenticatedUser.class);
        when(context.getSubject()).thenReturn(user);
        when(user.getUserName()).thenReturn("aofhbnf");
        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
        timestamp = "1514844324000";
        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp, "202.176.254.62"), 1);
    }


    // violates all the 3 rules
    @Test
    public void testCalculateRiskScore5() {
        log.info("Sending an authentication request violating 3 risk score rules ");
        connectionHandler = new ConnectionHandler();
        AuthenticationContext context = mock(AuthenticationContext.class);
        AuthenticatedUser user = mock(AuthenticatedUser.class);
        when(context.getSubject()).thenReturn(user);
        when(user.getUserName()).thenReturn("aofhbnf");
        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
        timestamp = "1519855524000";
        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp, "02.176.254.62"), 3);
    }

    @Test
    public void testResponseError() throws IOException {
        log.info("Testing response error code");
        AuthenticationContext context = mock(AuthenticationContext.class);
        AuthenticatedUser user = mock(AuthenticatedUser.class);
        when(context.getSubject()).thenReturn(user);
        when(user.getUserName()).thenReturn("pamoda");
        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
        timestamp = String.valueOf(System.currentTimeMillis());

        HttpClient mockHttpClient = mock(HttpClient.class);
        HttpResponse mockHttpResponse = mock(HttpResponse.class);
        HttpPost mockHttpPost = mock(HttpPost.class);
        StatusLine mockStatusLine = mock(StatusLine.class);

        when(mockHttpClient.execute(Matchers.any(HttpPost.class))).thenReturn(mockHttpResponse);
        when(mockStatusLine.getStatusCode()).thenReturn(404);
        when(mockHttpResponse.getStatusLine()).thenReturn(mockStatusLine);
        connectionHandler = new ConnectionHandler(mockHttpClient, mockHttpPost);
        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp, "203.43.1.43"), -1);
    }

    @Test
    public void testResponseDelay() throws IOException {
        log.info("Testing response delay");
        AuthenticationContext context = mock(AuthenticationContext.class);
        AuthenticatedUser user = mock(AuthenticatedUser.class);
        when(context.getSubject()).thenReturn(user);
        when(user.getUserName()).thenReturn("pamoda");
        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
        timestamp = String.valueOf(System.currentTimeMillis());

        HttpClient mockHttpClient = mock(HttpClient.class);
        HttpPost mockHttpPost = mock(HttpPost.class);

        when(mockHttpClient.execute(Matchers.any(HttpPost.class))).thenThrow(new SocketTimeoutException());
        connectionHandler = new ConnectionHandler(mockHttpClient, mockHttpPost);
        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp, "203.43.1.43"), -1);

    }

    @Test
    public void testConnectionError() throws IOException {
        log.info("Testing connection failure");
        AuthenticationContext context = mock(AuthenticationContext.class);
        AuthenticatedUser user = mock(AuthenticatedUser.class);
        when(context.getSubject()).thenReturn(user);
        when(user.getUserName()).thenReturn("pamoda");
        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");
        timestamp = String.valueOf(System.currentTimeMillis());

        HttpClient mockHttpClient = mock(HttpClient.class);
        HttpPost mockHttpPost = mock(HttpPost.class);

        when(mockHttpClient.execute(Matchers.any(HttpPost.class))).thenThrow(new ConnectException());
        connectionHandler = new ConnectionHandler(mockHttpClient, mockHttpPost);
        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp, "203.43.1.43"), -1);

    }


}
