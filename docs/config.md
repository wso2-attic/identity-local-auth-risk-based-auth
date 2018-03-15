#Configure Risk Based Adaptive Authentication

##Configuring WSO2 IS
>This feature is supported by WSO2 Identity Server 5.5.0

###Getting Started

Follow the steps to enable Risk Based Adaptive Authentication Feature in WSO2 IS.

1. Copy the [org.wso2.carbon.identity.authenticator.risk-1.0.0-SNAPSHOT.jar](../components/org.wso2.carbon.identity.authenticator.risk/target/org.wso2.carbon.identity.authenticator.risk-1.0.0-SNAPSHOTS.jar) into 
`<IS_HOME>/repository/components/dropins` directory
2. Add following configuration information in `identity.xml` file at `<IS_HOME>/repository/conf/identity` directory
    ```xml
     <Analytics>
        <Enabled>true</Enabled>
        <DASServerURL>https://localhost:9444</DASServerURL>
    </Analytics>
    ```
   * `<Enabled>true</Enabled>` enables Risk Based Adaptive Authentication. If you want to disable the feature make it 
  `false`
   * `<DASServerURL>https://localhost:9444</DASServerURL>` is the URL for the WSO2 IS Analytics Server
3. Start the server with the command `sh wso2server.sh -DenableConditionalAuthenticationFeature`

###Running the Sample

1. Configure a Service Provide with Multi-factor authentication steps. Follow the steps given [here](https://docs.wso2.com/pages/viewpage.action?pageId=85384955)
2. Now you can add a script to JavaScript based Conditional Steps in `Advanced Configuration` under Local & Outbound Authentication Configuration
![JavaScript Based Conditional Steps](/img/JavaScriptBasedConditionalSteps)
3. Add the following script and click `Enable Script`
    ```javascript
    function(context) {
       executeStep({
           id: '1',
           on: {
               success: function (context) {
    
                 // getRiskScore(context,properties) requires a key-value pair map as the second argument. 
                 // Since no properites are required, keep the map empty
                 var properties = {};
                 // properties.property1 = "test1";
                 // properties.property2 = "test2";
                 
                 var riskscore = getRiskScore(context , properties);
                 if (riskscore == 0) {
                    Log.info("No risk in the authentication request from user " + context.subject.authenticatedSubjectIdentifier);
                             Log.info(context.subject.authenticatedSubjectIdentifier);
                 }
                 else{
                    Log.info("High Risk authentication request from the user {"+
                             "Username : "+ context.subject.authenticatedSubjectIdentifier + ", " +
                             "UserStoreDomain : " +context.subject.userStoreDomain + ", " +
                             "TenantDomain : " +context.subject.tenantDomain + "}");
                    executeStep({id: '2'});
                    executeStep({id: '3'});
                }
               }  
           }
       });
    }
    ```
   * `getRiskScore(context, properties)` is the function used to evaluate the risk of the authentication request. 
   This function requires two mandatory arguments. 
        * context - the authentication context
        * properties - a key value pair map containing the additional properties. (The current risk calculation rules
         do not require any additional properties. Therefore send an empty map.)
   * After the successful authentication of first step, authentication flow is decided by the risk score. (You may 
   change the behaviour of the flow)
   
##Configuring WSO2 IS Analytics
