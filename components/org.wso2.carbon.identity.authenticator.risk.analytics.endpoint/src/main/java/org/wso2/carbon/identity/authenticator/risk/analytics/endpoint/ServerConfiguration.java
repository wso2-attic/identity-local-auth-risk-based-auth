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

/**
 * Container class to store configuration information of the IS-Analytics stored in file
 * <PRODUCT_HOME>/repository/conf/risk-calculator-config.xml
 */
public class ServerConfiguration {
    private String hostname;
    private String binaryTCPPort;
    private String binarySSLPort;
    private String HTTPSPort;
    private String username;
    private String password;
    private String authenticationStream;
    private String riskScoreStream;

    /**
     * Constructor enforcing to provide every parameter.
     *
     * @param hostname             Hostname of the IS-analytics node
     * @param binaryTCPPort        Binary TCP transport port of the IS-analytics node (Default : 9612)
     * @param binarySSLPort        Binary SSL transport port of the IS-analytics node : 9712)
     * @param HTTPSPort            HTTPS port of the global CEP node (Default : 9443)
     * @param username             Username of the user to authenticate to IS-analytics node before start sending events
     * @param password             Password of the user to authenticate to IS-analytics node before start sending events
     * @param authenticationStream stream name of authentication stream. case sensitive
     * @param riskScoreStream      stream name of riskScore stream. case sensitive
     */
    public ServerConfiguration(String hostname, String binaryTCPPort, String binarySSLPort, String HTTPSPort, String
            username, String password, String authenticationStream, String riskScoreStream) {
        this.hostname = hostname;
        this.binaryTCPPort = binaryTCPPort;
        this.binarySSLPort = binarySSLPort;
        this.HTTPSPort = HTTPSPort;
        this.username = username;
        this.password = password;
        this.authenticationStream = authenticationStream;
        this.riskScoreStream = riskScoreStream;
    }


    public String getHostname() {
        return hostname;
    }

    public String getBinaryTCPPort() {
        return binaryTCPPort;
    }

    public String getBinarySSLPort() {
        return binarySSLPort;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHTTPSPort() {
        return HTTPSPort;
    }

    public String getAuthenticationStream() {
        return authenticationStream;
    }

    public String getRiskScoreStream() {
        return riskScoreStream;
    }
}
