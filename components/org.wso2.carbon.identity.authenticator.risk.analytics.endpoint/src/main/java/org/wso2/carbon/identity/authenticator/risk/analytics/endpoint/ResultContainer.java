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

import org.apache.log4j.Logger;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.util.Constants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Container object blocks the request thread, collects result from IS-Analytics return the request thread
 */
public class ResultContainer {
    private static final Logger log = Logger.getLogger(ResultContainer.class);
    private CountDownLatch latch;
    //if the risk score is not received from IS-Analytics, it is set to the default value 2
    private int riskScore = Constants.DEFAULT_RISK_SCORE;

    public ResultContainer() {
        latch = new CountDownLatch(1);
    }

    /**
     * Upon receiving a result from riskscore stream this method will update the result list and handle locking
     * mechanisms.
     *
     * @param score risk score for the request
     */
    public void addResult(int score) {
        riskScore = score;
        latch.countDown();
        if (log.isDebugEnabled()) {
            log.debug("Result is added to the container. Releasing the thread");
        }
    }

    /**
     * Wait for other threads to post results
     *
     * @return risk score
     */
    public int getRiskScoreDTO() throws InterruptedException {
        // TODO: 2/21/18 timeout should be configured not hardcoded
        latch.await(1, TimeUnit.SECONDS);
        return riskScore;
    }

}
