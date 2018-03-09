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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.databridge.commons.StreamDefinition;
import org.wso2.carbon.event.stream.core.WSO2EventConsumer;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.util.CarbonServiceValueHolder;

/**
 * Event consumer class to consume risk score stream. Correlation is made using streamID which is extracted from server
 * config.
 */
public class RiskScoreStreamConsumer implements WSO2EventConsumer {
    private static final Log log = LogFactory.getLog(RiskScoreStreamConsumer.class);
    private String streamID;

    public RiskScoreStreamConsumer(String streamID) {
        this.streamID = streamID;
    }

    @Override
    public String getStreamId() {
        return streamID;
    }

    /**
     * This method is called upon receiving new events for risk score stream.
     * Result is added to the corresponding result container
     *
     * @param event Resultant event from the IS-Analytics
     */
    public void onEvent(Event event) {
        if (log.isDebugEnabled()) {
            log.debug("Response is received from IS-Analytics");
        }
        ResultContainer container = CarbonServiceValueHolder.getResultContainerMap()
                .get(String.valueOf(event.getPayloadData()[0]));
        if (container != null) {
            container.addResult((Integer) event.getPayloadData()[1]);
        }
    }

    @Override
    public void onAddDefinition(StreamDefinition streamDefinition) {
        //Do nothing
    }

    @Override
    public void onRemoveDefinition(StreamDefinition streamDefinition) {
        //Do nothing
    }
}
