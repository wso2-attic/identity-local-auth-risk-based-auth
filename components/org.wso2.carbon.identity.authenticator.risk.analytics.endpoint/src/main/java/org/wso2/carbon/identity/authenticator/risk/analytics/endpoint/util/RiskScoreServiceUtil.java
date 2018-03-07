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

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.ServerConfiguration;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.exceptions.RiskScoreServiceConfigurationException;
import org.wso2.carbon.utils.ServerConstants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class RiskScoreServiceUtil {
    private static final Log log = LogFactory.getLog(RiskScoreServiceUtil.class);

    /**
     * Read server config file at repository/conf/is-analytics-config.xml and returns a POJO representing that.
     *
     * @return POJO representing the configuration file
     * @throws RiskScoreServiceConfigurationException exception in configuring the service
     */
    public static ServerConfiguration loadServerConfig() throws RiskScoreServiceConfigurationException {
        String carbonHome = System.getProperty(ServerConstants.CARBON_CONFIG_DIR_PATH);
        String path = carbonHome + File.separator + Constants.IS_ANALYTICS_CONFIG_XML;
        OMElement configElement = loadConfigXML(path);

        OMElement hostNameElement;
        OMElement tcpPortElement;
        OMElement sslPortElement;
        OMElement httpsPortElement;
        OMElement usernameElement;
        OMElement passwordElement;
        OMElement authenticationStreamElement;
        OMElement riskScoreStreamElement;

        if ((hostNameElement = configElement.getFirstChildWithName(new QName(Constants.HOST_NAME))) == null) {
            throw new RiskScoreServiceConfigurationException("Invalid config element with no host name in " +
                    Constants.IS_ANALYTICS_CONFIG_XML);
        }
        if ((tcpPortElement = configElement.getFirstChildWithName(new QName(Constants.TCP_PORT))) == null) {
            throw new RiskScoreServiceConfigurationException("Invalid config element with no TCP port in " +
                    Constants.IS_ANALYTICS_CONFIG_XML);
        }
        if ((httpsPortElement = configElement.getFirstChildWithName(new QName(Constants.HTTPS_PORT))) == null) {
            throw new RiskScoreServiceConfigurationException("Invalid config element with no HTTPS port in " +
                    Constants.IS_ANALYTICS_CONFIG_XML);
        }
        if ((sslPortElement = configElement.getFirstChildWithName(new QName(Constants.SSL_PORT))) == null) {
            throw new RiskScoreServiceConfigurationException("Invalid config element with no SSL port in " +
                    Constants.IS_ANALYTICS_CONFIG_XML);
        }
        if ((usernameElement = configElement.getFirstChildWithName(new QName(Constants.USERNAME))) == null) {
            throw new RiskScoreServiceConfigurationException("Invalid config element with no username in " +
                    Constants.IS_ANALYTICS_CONFIG_XML);
        }
        if ((passwordElement = configElement.getFirstChildWithName(new QName(Constants.PASSWORD))) == null) {
            throw new RiskScoreServiceConfigurationException("Invalid config element with no password in " +
                    Constants.IS_ANALYTICS_CONFIG_XML);
        }
        if ((authenticationStreamElement = configElement.getFirstChildWithName(new QName(Constants
                .AUTHENTICATION_STREAM))) == null) {
            throw new RiskScoreServiceConfigurationException("Invalid config element with no authentication stream in" +
                    " " +
                    Constants.IS_ANALYTICS_CONFIG_XML);
        }
        if ((riskScoreStreamElement = configElement.getFirstChildWithName(new QName(Constants.RISK_SCORE_STREAM))) ==
                null) {
            throw new RiskScoreServiceConfigurationException("Invalid config element with no riskscore stream in " +
                    Constants.IS_ANALYTICS_CONFIG_XML);
        }


        return new ServerConfiguration(hostNameElement.getText(), tcpPortElement.getText(), sslPortElement.getText(),
                httpsPortElement.getText(), usernameElement.getText(), passwordElement.getText(),
                authenticationStreamElement
                        .getText(), riskScoreStreamElement.getText());
    }

    /**
     * Loads the configuration file in the given path as an OM element
     *
     * @return OMElement of config file
     * @throws RiskScoreServiceConfigurationException exception in configuring the service
     */
    private static OMElement loadConfigXML(String path) throws RiskScoreServiceConfigurationException {

        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(new File(path)));
            XMLStreamReader parser = XMLInputFactory.newInstance().
                    createXMLStreamReader(inputStream);
            StAXOMBuilder builder = new StAXOMBuilder(parser);
            OMElement omElement = builder.getDocumentElement();
            omElement.build();
            return omElement;
        } catch (FileNotFoundException e) {
            throw new RiskScoreServiceConfigurationException("Configuration file cannot be found in the path : " +
                    path, e);
        } catch (XMLStreamException e) {
            throw new RiskScoreServiceConfigurationException("Invalid XML syntax for configuration file located in " +
                    "the path :" + path, e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("Can not shutdown the input stream", e);
            }
        }
    }
}
