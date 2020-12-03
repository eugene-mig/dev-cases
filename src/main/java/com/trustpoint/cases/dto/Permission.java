package com.trustpoint.cases.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustpoint.cases.constants.RoleKind;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Permission {
  private String policy;
  private String permission;
  @JsonProperty("role_kind")
  private RoleKind roleKind;
  @JsonProperty("business_unit")
  private String businessUnit;
}
