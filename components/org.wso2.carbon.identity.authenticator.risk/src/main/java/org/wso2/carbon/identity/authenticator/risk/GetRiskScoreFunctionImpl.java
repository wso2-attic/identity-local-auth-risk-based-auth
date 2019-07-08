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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;
import org.wso2.carbon.identity.authenticator.risk.exception.RiskScoreCalculationException;
import org.wso2.carbon.identity.authenticator.risk.model.RiskScoreRequestDTO;
import org.wso2.carbon.identity.authenticator.risk.util.RiskScoreConstants;
import org.wso2.carbon.identity.core.util.IdentityUtil;

import java.util.Map;

/**
 * Implementation of the javascript function to obtain risk score of the authentication requests.
 */
public class GetRiskScoreFunctionImpl implements GetRiskScoreFunction {
    private static final Log log = LogFactory.getLog(GetRiskScoreFunctionImpl.class);

    /**
     * Get the risk score from the IS analytics.
     *
     * @param context     JsAuthentication Context
     * @param propertyMap additional properties
     * @return risk score for the authentication request
     */
    @Override
    public int getRiskScore(JsAuthenticationContext context, Map<String, String> propertyMap) {
        RiskScoreRequestDTO requestDTO = new RiskScoreRequestDTO(context.getWrapped(), propertyMap);

        // If the risk can not be calculated, it is set to the default score of high risk.
        int riskScore = RiskScoreConstants.DEFAULT_RISK_SCORE;

        // Check whether Analytics is enabled in the identity.xml
        Boolean isEnabled = Boolean.parseBoolean(IdentityUtil.getProperty(RiskScoreConstants.ANALYTICS_ENABLED));

        try {
            if (isEnabled) {
                ConnectionHandler handler = new ConnectionHandler();
                riskScore = handler.calculateRiskScore(requestDTO);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Risk score is not calculated. Analytics is disabled.");
                }
            }
        } catch (RiskScoreCalculationException e) {
            log.warn("Could not calculate risk score. " + e.getMessage(), e);
        }
        return riskScore;
    }
}
