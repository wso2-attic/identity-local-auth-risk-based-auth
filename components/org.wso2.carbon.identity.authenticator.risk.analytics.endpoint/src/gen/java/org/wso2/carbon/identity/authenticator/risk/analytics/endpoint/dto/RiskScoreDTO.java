package org.wso2.carbon.identity.authenticator.risk.analytics.endpoint.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class RiskScoreDTO  {
  
  
  @NotNull
  private Integer score = 2;

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("score")
  public Integer getScore() {
    return score;
  }
  public void setScore(Integer score) {
    this.score = score;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class RiskScoreDTO {\n");
    
    sb.append("  score: ").append(score).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
