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

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.databridge.agent.DataPublisher;
import org.wso2.carbon.databridge.agent.exception.DataEndpointAgentConfigurationException;
import org.wso2.carbon.databridge.agent.exception.DataEndpointAuthenticationException;
import org.wso2.carbon.databridge.agent.exception.DataEndpointConfigurationException;
import org.wso2.carbon.databridge.agent.exception.DataEndpointException;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.databridge.commons.exception.TransportException;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.AuthRequestDTO;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.exception.RiskScoreServiceConfigurationException;

/**
 * Event Publisher class to publish incoming requests as events to CEP engine.
 */
public class EventPublisher {
    private static final Log log = LogFactory.getLog(EventPublisher.class);
    private DataPublisher dataPublisher;
    private ServerConfiguration serverConfiguration;

    public EventPublisher(ServerConfiguration serverConfiguration) throws RiskScoreServiceConfigurationException {
        this.serverConfiguration = serverConfiguration;
        try {
            this.dataPublisher = new DataPublisher("Binary", "tcp://" + serverConfiguration.getHostname() +
                    ":" + serverConfiguration.getBinaryTCPPort(), "ssl://" + serverConfiguration.getHostname() +
                    ":" + serverConfiguration.getBinarySSLPort(), serverConfiguration.getUsername(),
                    serverConfiguration.getPassword());
            if (log.isDebugEnabled()) {
                log.debug("Initiated binary data publisher.");
            }

        } catch (DataEndpointAgentConfigurationException | DataEndpointException |
                DataEndpointAuthenticationException | DataEndpointConfigurationException | TransportException e) {
            throw new RiskScoreServiceConfigurationException("Error in initializing binary data-publisher to send " +
                    "requests to the server ", e);
        }
    }

    /**
     * Create and send an event matching with the stream definition in IS analytics.
     *
     * @param authRequest authentication request object from the API service
     * @param streamID    risk score request streamID
     */
    public void sendEvent(AuthRequestDTO authRequest, String streamID) {

        Object[] payloadData = new Object[14];
        payloadData[0] = streamID;
        payloadData[1] = authRequest.getUsername();
        payloadData[2] = authRequest.getUserStoreDomain();
        payloadData[3] = authRequest.getTenantDomain();
        payloadData[4] = authRequest.getRemoteIp();
        payloadData[5] = Long.parseLong(authRequest.getTimestamp());
        payloadData[6] = authRequest.getInboundAuthType();
        payloadData[7] = authRequest.getServiceProvider();
        payloadData[8] = authRequest.getRememberMeEnabled();
        payloadData[9] = authRequest.getForceAuthEnabled();
        payloadData[10] = authRequest.getPassiveAuthEnabled();
        payloadData[11] = authRequest.getIdentityProvider();
        payloadData[12] = authRequest.getStepAuthenticator();
        Gson gson = new Gson();
        String propertyString = gson.toJson(authRequest.getPropertyMap());
        payloadData[13] = propertyString;

        Event event = new Event(serverConfiguration.getAuthenticationStream(), System.currentTimeMillis(), null,
                null, payloadData);
        if (log.isDebugEnabled()) {
            log.debug("Sending events to IS-Analytics.");
        }
        dataPublisher.publish(event);
    }
}
