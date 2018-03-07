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
package org.wso2.carbon.identity.authenticator.risk.analytics.siddhi.extension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.template.manager.core.exception.TemplateManagerException;
import org.wso2.carbon.identity.authenticator.risk.analytics.internal.TemplateManagerConfigurationServiceHolder;
import org.wso2.siddhi.core.config.ExecutionPlanContext;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.query.api.definition.Attribute;

/**
 * Siddhi extension to count the number of rules deployed in IS-Analytics
 */
public class RuleCounterExtension extends FunctionExecutor {
    private static final Log log = LogFactory.getLog(RuleCounterExtension.class);

    @Override
    protected void init(ExpressionExecutor[] expressionExecutors, ExecutionPlanContext executionPlanContext) {

    }

    @Override
    protected Object execute(Object[] objects) {
        return null;
    }

    @Override
    protected Object execute(Object o) {
        int ruleCount = 0;

        try {
            ruleCount = TemplateManagerConfigurationServiceHolder.getTemplateManagerService().getConfigurations(o
                    .toString()).size();
        } catch (TemplateManagerException e) {
            log.error("Failed to get the number of rules deployed. " + e.getMessage(), e);
        }
        return ruleCount;
    }

    @Override
    public Attribute.Type getReturnType() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public Object[] currentState() {
        return new Object[0];
    }

    @Override
    public void restoreState(Object[] objects) {

    }
}
