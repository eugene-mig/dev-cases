package com.trustpoint.cases.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CaseFilterPayload {
  @JsonProperty("customer_id")
  private String customerId;
}
