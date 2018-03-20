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
package org.wso2.carbon.identity.authenticator.risk.util;

/**
 * Contains the constants required for the Adaptive Authentication.
 */
public class RiskScoreConstants {
    public static final String RISK_SCORE_SERVICE_PATH = "/api/authentication/adaptive/risk/calculate";
    public static final int DEFAULT_RISK_SCORE = 2;
    public static final String SECURITY_KEYSTORE_LOCATION = "Security.KeyStore.Location";
    public static final String SECURITY_KEYSTORE_PASSWORD = "Security.KeyStore.Password";
    public static final String SECURITY_KEYSTORE_TYPE = "Security.KeyStore.Type";
    public static final String ANALYTICS_DAS_SERVER_URL = "Analytics.DASServerURL";
    public static final String ANALYTICS_ENABLED = "Analytics.Enabled";
}
