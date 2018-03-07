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
package identity.anlaytics.riskscore;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto.AuthRequestDTO;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * TODO: Class level comments
 */
@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(PrivilegedCarbonContext.class)
public class GetRiskScoreApiServiceTest {
    @BeforeMethod
    public void setUp() {
    }


    @Test
    public void testGetRiskScoreApiService() throws Exception {
        AuthRequestDTO mockAuthRequest = mock(AuthRequestDTO.class);
//        GetRiskScoreApiServiceImpl mockApiService =mock(GetRiskScoreApiServiceImpl.class);
//        whenNew(GetRiskScoreApiServiceImpl.class).withNoArguments().thenReturn(mockApiService);
        when(mockAuthRequest.getUsername()).thenReturn("pamoda");
        when(mockAuthRequest.getUserStoreDomain()).thenReturn("PRIMARY");
        when(mockAuthRequest.getTenantDomain()).thenReturn("carbon.super");
        when(mockAuthRequest.getRemoteIp()).thenReturn("230.10.10.23");
        when(mockAuthRequest.getTimestamp()).thenReturn(String.valueOf(System.currentTimeMillis()));
//        GetRiskScoreApiService mockApiService = new GetRiskScoreApiServiceImpl();
//        Assert.assertEquals(mockApiService.getRiskScore(mockAuthRequest).getStatus(), 200);
    }
}
