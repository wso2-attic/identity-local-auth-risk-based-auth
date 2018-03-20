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

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.ServerConfiguration;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.exception.RiskScoreServiceConfigurationException;
import org.wso2.carbon.utils.ServerConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test RiskScore Service Util class.
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.stream.XMLInputFactory", "javax.xml.transform.Transformer"})
public class RiskScoreServiceUtilTest {

    private String pathToConfigFileLocation;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(Constants.RISK_CALCULATOR_CONFIG_XML)).getPath();
        int index = path.lastIndexOf(File.separator);
        pathToConfigFileLocation = path.substring(0, index);
    }

    @Test
    public void testFileLoad() throws Exception {
        System.setProperty(ServerConstants.CARBON_CONFIG_DIR_PATH, pathToConfigFileLocation);
        ServerConfiguration serverConfiguration = RiskScoreServiceUtil.loadServerConfig();
        Assert.assertNotNull(serverConfiguration);
    }

    @Test
    public void testMissingHostName() throws Exception {
        System.setProperty(ServerConstants.CARBON_CONFIG_DIR_PATH, pathToConfigFileLocation);
        deleteNode(Constants.HOST_NAME);
        try {
            RiskScoreServiceUtil.loadServerConfig();
        } catch (RiskScoreServiceConfigurationException e) {
            Assert.assertNotNull(e);
            Assert.assertEquals(e.getMessage(), "Invalid config element with no host name in " +
                    Constants.RISK_CALCULATOR_CONFIG_XML);
        }
        appendNode(Constants.HOST_NAME, "localhost");
    }

    @Test
    public void testMissingTCPport() throws Exception {
        System.setProperty(ServerConstants.CARBON_CONFIG_DIR_PATH, pathToConfigFileLocation);
        deleteNode(Constants.TCP_PORT);
        try {
            RiskScoreServiceUtil.loadServerConfig();
        } catch (RiskScoreServiceConfigurationException e) {
            Assert.assertNotNull(e);
            Assert.assertEquals(e.getMessage(), "Invalid config element with no TCP port in " +
                    Constants.RISK_CALCULATOR_CONFIG_XML);
        }
        appendNode(Constants.TCP_PORT, "9612");
    }

    @Test
    public void testMissingHTTPSport() throws Exception {
        System.setProperty(ServerConstants.CARBON_CONFIG_DIR_PATH, pathToConfigFileLocation);
        deleteNode(Constants.HTTPS_PORT);
        try {
            RiskScoreServiceUtil.loadServerConfig();
        } catch (RiskScoreServiceConfigurationException e) {
            Assert.assertNotNull(e);
            Assert.assertEquals(e.getMessage(), "Invalid config element with no HTTPS port in " +
                    Constants.RISK_CALCULATOR_CONFIG_XML);
        }
        appendNode(Constants.HTTPS_PORT, "9444");
    }

    @Test
    public void testMissingSSLport() throws Exception {
        System.setProperty(ServerConstants.CARBON_CONFIG_DIR_PATH, pathToConfigFileLocation);
        deleteNode(Constants.SSL_PORT);
        try {
            RiskScoreServiceUtil.loadServerConfig();
        } catch (RiskScoreServiceConfigurationException e) {
            Assert.assertNotNull(e);
            Assert.assertEquals(e.getMessage(), "Invalid config element with no SSL port in " +
                    Constants.RISK_CALCULATOR_CONFIG_XML);
        }
        appendNode(Constants.SSL_PORT, "9712");
    }

    @Test
    public void testMissingUserName() throws Exception {
        System.setProperty(ServerConstants.CARBON_CONFIG_DIR_PATH, pathToConfigFileLocation);
        deleteNode(Constants.USERNAME);
        try {
            RiskScoreServiceUtil.loadServerConfig();
        } catch (RiskScoreServiceConfigurationException e) {
            Assert.assertNotNull(e);
            Assert.assertEquals(e.getMessage(), "Invalid config element with no username in " +
                    Constants.RISK_CALCULATOR_CONFIG_XML);
        }
        appendNode(Constants.USERNAME, "admin");
    }

    @Test
    public void testMissingPassword() throws Exception {
        System.setProperty(ServerConstants.CARBON_CONFIG_DIR_PATH, pathToConfigFileLocation);
        deleteNode(Constants.PASSWORD);
        try {
            RiskScoreServiceUtil.loadServerConfig();
        } catch (RiskScoreServiceConfigurationException e) {
            Assert.assertNotNull(e);
            Assert.assertEquals(e.getMessage(), "Invalid config element with no password in " +
                    Constants.RISK_CALCULATOR_CONFIG_XML);
        }
        appendNode(Constants.PASSWORD, "admin");
    }

    @Test
    public void testMissingAuthStream() throws Exception {
        System.setProperty(ServerConstants.CARBON_CONFIG_DIR_PATH, pathToConfigFileLocation);
        deleteNode(Constants.AUTHENTICATION_STREAM);
        try {
            RiskScoreServiceUtil.loadServerConfig();
        } catch (RiskScoreServiceConfigurationException e) {
            Assert.assertNotNull(e);
            Assert.assertEquals(e.getMessage(), "Invalid config element with no authentication stream in " +
                    Constants.RISK_CALCULATOR_CONFIG_XML);
        }
        appendNode(Constants.AUTHENTICATION_STREAM, "org.wso2.is.analytics.stream.RiskScoreRequest:1.0.0");
    }

    @Test
    public void testMissingRiskScoreStream() throws Exception {
        System.setProperty(ServerConstants.CARBON_CONFIG_DIR_PATH, pathToConfigFileLocation);
        deleteNode(Constants.RISK_SCORE_STREAM);
        try {
            RiskScoreServiceUtil.loadServerConfig();
        } catch (RiskScoreServiceConfigurationException e) {
            Assert.assertNotNull(e);
            Assert.assertEquals(e.getMessage(), "Invalid config element with no risk score stream in " +
                    Constants.RISK_CALCULATOR_CONFIG_XML);
        }
        appendNode(Constants.RISK_SCORE_STREAM, "org.wso2.is.analytics.stream.RiskScorePerRule:1.0.0");
    }


    @Test(expectedExceptions = RiskScoreServiceConfigurationException.class)
    public void testFileNotFoundException() throws RiskScoreServiceConfigurationException {
        System.setProperty(ServerConstants.CARBON_CONFIG_DIR_PATH, "null");
        try {
            RiskScoreServiceUtil.loadServerConfig();
        } catch (RiskScoreServiceConfigurationException e) {
            Assert.assertEquals(e.getCause().getClass(), FileNotFoundException.class);
            throw e;
        }
    }

    private void deleteNode(String nodeName) throws Exception {
        String path = pathToConfigFileLocation + File.separator + Constants.RISK_CALCULATOR_CONFIG_XML;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(path);
        Node CEPConfig = document.getElementsByTagName("CEPConfig").item(0);
        NodeList nodes = CEPConfig.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node element = nodes.item(i);
            if (nodeName.equals(element.getNodeName())) {
                CEPConfig.removeChild(element);
            }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(path).getPath());
        transformer.transform(domSource, streamResult);
    }

    private void appendNode(String nodeName, String value) throws Exception {
        String path = pathToConfigFileLocation + File.separator + Constants.RISK_CALCULATOR_CONFIG_XML;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(path);
        Node CEPConfig = document.getElementsByTagName("CEPConfig").item(0);
        Element element = document.createElement(nodeName);
        element.appendChild(document.createTextNode(value));
        CEPConfig.appendChild(element);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(path).getPath());
        transformer.transform(domSource, streamResult);
    }
}
