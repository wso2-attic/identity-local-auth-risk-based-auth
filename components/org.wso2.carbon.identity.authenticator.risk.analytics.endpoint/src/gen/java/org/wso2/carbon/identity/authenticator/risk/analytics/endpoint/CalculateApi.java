package org.wso2.carbon.identity.authenticator.risk.analytics.endpoint;

import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.*;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.CalculateApiService;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.factories.CalculateApiServiceFactory;

import io.swagger.annotations.ApiParam;

import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.AuthRequestDTO;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.RiskScoreDTO;

import java.util.List;

import java.io.InputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/calculate")


@io.swagger.annotations.Api(value = "/calculate", description = "the calculate API")
public class CalculateApi  {

   private final CalculateApiService delegate = CalculateApiServiceFactory.getCalculateApi();

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Method to obtain the calculated risk score an authentication request", response = RiskScoreDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Risk Score") })

    public Response calculateRiskScore(@ApiParam(value = "authentication request by the user" ,required=true ) AuthRequestDTO authRequest)
    {
    return delegate.calculateRiskScore(authRequest);
    }
}

