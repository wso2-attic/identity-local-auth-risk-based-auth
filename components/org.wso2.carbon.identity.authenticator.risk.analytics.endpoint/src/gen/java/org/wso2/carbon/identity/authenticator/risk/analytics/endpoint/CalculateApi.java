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

import io.swagger.annotations.ApiParam;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.AuthRequestDTO;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.RiskScoreDTO;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.factories.CalculateApiServiceFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/calculate")
@io.swagger.annotations.Api(value = "/calculate", description = "the calculate API")
public class CalculateApi {

    private final CalculateApiService delegate = CalculateApiServiceFactory.getCalculateApi();

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @io.swagger.annotations.ApiOperation(value = "", notes = "Method to obtain the calculated risk score an " +
            "authentication request", response = RiskScoreDTO.class)
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Risk Score")})

    public Response calculateRiskScore(@ApiParam(value = "authentication request by the user", required = true)
                                               AuthRequestDTO authRequest) {
        return delegate.calculateRiskScore(authRequest);
    }
}
