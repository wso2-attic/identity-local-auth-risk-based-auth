package org.wso2.carbon.identity.authenticator.risk.analytics.endpoint;

import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.*;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.*;

import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.AuthRequestDTO;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.RiskScoreDTO;

import java.util.List;

import java.io.InputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import javax.ws.rs.core.Response;

public abstract class CalculateApiService {
    public abstract Response calculateRiskScore(AuthRequestDTO authRequest);
}

