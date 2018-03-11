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

import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.event.stream.core.EventStreamService;
import org.wso2.carbon.event.template.manager.core.TemplateManagerService;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.ResultContainer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Holder object to hold references which are needed by multiple classes.
 */
public class CarbonServiceValueHolder {
    private static CarbonServiceValueHolder instance;
    private static EventStreamService eventStreamService;
    private static TemplateManagerService templateManagerService;
    private static Map<String, ResultContainer> resultContainerMap = new ConcurrentHashMap<>();

    private CarbonServiceValueHolder() {
        //DO nothing
    }

    public static CarbonServiceValueHolder getInstance() {
        if (instance == null) {
            instance = new CarbonServiceValueHolder();
        }
        return instance;
    }

    public EventStreamService getEventStreamService() {
        if (eventStreamService == null) {
            eventStreamService = (EventStreamService) PrivilegedCarbonContext.getThreadLocalCarbonContext()
                    .getOSGiService(EventStreamService.class, null);
        }
        return eventStreamService;
    }

    public void setEventStreamService(EventStreamService eventStreamService) {
        CarbonServiceValueHolder.eventStreamService = eventStreamService;
    }

    public Map<String, ResultContainer> getResultContainerMap() {
        return resultContainerMap;
    }

    public void setResultContainerMap(Map<String, ResultContainer> resultContainerMap) {
        CarbonServiceValueHolder.resultContainerMap = resultContainerMap;
    }

    public TemplateManagerService getTemplateManagerService() {
        if (templateManagerService == null) {
            templateManagerService = (TemplateManagerService) PrivilegedCarbonContext.getThreadLocalCarbonContext()
                    .getOSGiService(TemplateManagerService.class, null);
        }
        return templateManagerService;
    }

    public void setTemplateManagerService(TemplateManagerService templateManagerService) {
        CarbonServiceValueHolder.templateManagerService = templateManagerService;
    }
}
