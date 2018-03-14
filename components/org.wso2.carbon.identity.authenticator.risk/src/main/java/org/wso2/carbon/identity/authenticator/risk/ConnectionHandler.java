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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.wso2.carbon.identity.authenticator.risk.exception.RiskScoreCalculationException;
import org.wso2.carbon.identity.authenticator.risk.model.RiskScoreDTO;
import org.wso2.carbon.identity.authenticator.risk.model.RiskScoreRequestDTO;
import org.wso2.carbon.identity.authenticator.risk.util.RiskScoreConstants;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.utils.CarbonUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Handle the Http connection
 */
public class ConnectionHandler {
    private static final Log log = LogFactory.getLog(ConnectionHandler.class);

    /**
     * send the authentication request data to the IS analytics and obtain the risk score
     *
     * @param requestDTO Authentication context
     * @return riskScore riskScore value for the authentication request
     */
    public int calculateRiskScore(RiskScoreRequestDTO requestDTO) throws RiskScoreCalculationException {
        // Building the http request
        String baseURL = IdentityUtil.getProperty("Analytics.DASServerURL");
        HttpPost httpPost = new HttpPost(baseURL+RiskScoreConstants.RISK_SCORE_SERVICE_PATH);
        httpPost.setEntity(createRequestBody(requestDTO));
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(1000)
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(1000)
                .build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-type", "application/json");

        // Building the http client
        CloseableHttpClient httpClient;
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

            String pathToKeyStore = CarbonUtils.getServerConfiguration().getFirstProperty("Security.KeyStore.Location");
            String password = CarbonUtils.getServerConfiguration().getFirstProperty("Security.KeyStore.Password");
            String type = CarbonUtils.getServerConfiguration().getFirstProperty("Security.KeyStore.Type");
            KeyStore keyStore = KeyStore.getInstance(type);
            InputStream inputStream = new FileInputStream(pathToKeyStore);
            keyStore.load(inputStream, password.toCharArray());
            httpClientBuilder.setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
                    .loadTrustMaterial(keyStore).build()));
            httpClient = httpClientBuilder.build();
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | KeyManagementException |
                IOException e) {
            throw new RiskScoreCalculationException("Failed to establish a secure connection", e);
        }

        int riskScore;

        // Execute the API call and obtain the result
        CloseableHttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                riskScore = getScoreFromResponse(httpResponse);
            } else {
                throw new RiskScoreCalculationException("HTTP error code : " + httpResponse.getStatusLine()
                        .getStatusCode());
            }
        } catch (IOException e) {
            throw new RiskScoreCalculationException("Failed to connect with the server", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                if (log.isDebugEnabled()) {
                    log.debug("Failed to close the Http Client. " + e.getMessage(), e);
                }
            }
        }
        return riskScore;
    }

    private StringEntity createRequestBody(RiskScoreRequestDTO riskScoreRequestDTO) throws
            RiskScoreCalculationException {
        ObjectMapper mapper = new ObjectMapper();
        StringEntity requestBody;
        try {
            String requestBodyInString = mapper.writeValueAsString(riskScoreRequestDTO);
            requestBody = new StringEntity(requestBodyInString);
            requestBody.setContentType("application/json");
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RiskScoreCalculationException("Failed to initialize http request body", e);
        }
        return requestBody;
    }

    private int getScoreFromResponse(CloseableHttpResponse response) throws RiskScoreCalculationException {
        ObjectMapper mapper = new ObjectMapper();
        int riskScore;
        try {
            String responseString = EntityUtils.toString(response.getEntity());
            RiskScoreDTO riskScoreDTO = mapper.readValue(responseString, RiskScoreDTO.class);
            riskScore = riskScoreDTO.getScore();
        } catch (IOException e) {
            throw new RiskScoreCalculationException("Failed to get risk score from response", e);
        }
        return riskScore;
    }
}



