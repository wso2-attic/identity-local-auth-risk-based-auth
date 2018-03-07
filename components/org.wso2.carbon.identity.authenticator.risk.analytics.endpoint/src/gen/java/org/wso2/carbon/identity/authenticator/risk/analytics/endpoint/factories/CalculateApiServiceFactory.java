package org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.factories;

import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.CalculateApiService;
import org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.impl.CalculateApiServiceImpl;

public class CalculateApiServiceFactory {

   private final static CalculateApiService service = new CalculateApiServiceImpl();

   public static CalculateApiService getCalculateApi()
   {
      return service;
   }
}
