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

import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.adaptive.authentication.riskscore.util.ConnectionHandler;
import org.wso2.carbon.identity.adaptive.authentication.riskscore.util.RiskScoreConstants;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * TODO: Class level comments
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SSLContexts.class})
public class ConnectionHandlerTest {
    private static final String RESPONSE_AS_STRING = "ResponseAsString";
    private final static String URL = RiskScoreConstants.URL;
    private final static String CONTENT_TYPE = "application/json";
    private final static String ENTITY = "{}";

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpConnectionManager mockConnectionManager;

    @Mock
    private HttpPost mockRequest;

    private HttpResponse mockResponse;

    @Mock
    private ConnectionHandler connectionHandler;

    @Mock
    private AuthenticationContext context;

    private String timestamp;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SSLContexts.class);

        AuthenticatedUser user = mock(AuthenticatedUser.class);
        timestamp = String.valueOf(System.currentTimeMillis());

        when(context.getSubject()).thenReturn(user);
        when(user.getUserName()).thenReturn("pamoda");
        when(context.getSubject().getUserStoreDomain()).thenReturn("PRIMARY");
        when(context.getSubject().getTenantDomain()).thenReturn("carbon.super");

    }

    @Test
    public void testCalculateRiskScore() {
        connectionHandler.calculateRiskScore(context, timestamp);
    }

    @Test
    public void testHttpClientBuilder() throws KeyStoreException, NoSuchAlgorithmException {

        SSLContextBuilder sslContextBuilder = mock(SSLContextBuilder.class);
        when(sslContextBuilder.loadTrustMaterial(isNull(KeyStore.class), any(TrustSelfSignedStrategy.class)))
                .thenThrow(new NoSuchAlgorithmException());

        when(SSLContexts.custom()).thenReturn(sslContextBuilder);

        Assert.assertEquals(connectionHandler.calculateRiskScore(context, timestamp), -1);
    }

    @Test
    public void testRequestBodyInitiation() {

    }

    @Test
    public void testNullResponse() {

    }

    @Test
    public void testResponseError() {

    }


}
