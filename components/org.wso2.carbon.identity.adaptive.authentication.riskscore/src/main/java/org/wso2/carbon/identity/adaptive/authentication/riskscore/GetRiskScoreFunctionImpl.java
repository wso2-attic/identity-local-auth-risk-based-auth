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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.adaptive.authentication.riskscore.exception.RiskScoreCalculationException;
import org.wso2.carbon.identity.adaptive.authentication.riskscore.util.ConnectionHandler;
import org.wso2.carbon.identity.adaptive.authentication.riskscore.util.RiskScoreRequestDTO;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;

/**
 * Implementation of the javascript function to obtain risk score of the authentication requests
 */
public class GetRiskScoreFunctionImpl implements GetRiskScoreFunction {
    private static final Log log = LogFactory.getLog(GetRiskScoreFunctionImpl.class);
    @Override
    public int getRiskScore(JsAuthenticationContext context) {

        RiskScoreRequestDTO requestDTO = new RiskScoreRequestDTO(context.getWrapped());
        ConnectionHandler handler = new ConnectionHandler();
        int riskScore;
        try {
            riskScore = handler.calculateRiskScore(requestDTO);
        } catch (RiskScoreCalculationException e) {
            log.warn("Could not calculate risk score. " + e.getMessage(), e);
            riskScore = e.getRiskScore();
        }
        return riskScore;
    }


}
