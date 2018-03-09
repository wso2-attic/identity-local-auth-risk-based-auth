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

public class Constants {

    private Constants() {
        //avoids initialization
    }

    public static final String IS_ANALYTICS_CONFIG_XML = "is-analytics-config.xml";
    public static final String CONFIG_ELEMENT = "CEPConfig";
    public static final String HOST_NAME = "hostName";
    public static final String TCP_PORT = "binaryTCPPort";
    public static final String SSL_PORT = "binarySSLPort";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String AUTHENTICATION_STREAM = "authenticationStream";
    public static final String RISK_SCORE_STREAM = "riskScoreStream";
    public static final String HTTPS_PORT = "HTTPSPort";

    public static final int DEFAULT_RISK_SCORE = 2;
    public static final String TEMPLATE_MANAGER_DOMAIN_NAME = "RiskScoreCalculator";

}
