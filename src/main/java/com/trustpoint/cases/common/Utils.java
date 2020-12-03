package com.trustpoint.cases.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustpoint.cases.dto.BusinessUnit;
import com.trustpoint.cases.dto.Permission;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Utils {
  public static List<Permission> getRequestPermissions(Map<String, String> headers) throws Exception {
    String permissionsJson = headers.get("permissions");
    ObjectMapper MAPPER = new ObjectMapper();

    if (!StringUtils.isEmpty(permissionsJson)) {
      Permission[] permissions = MAPPER.readValue(permissionsJson, Permission[].class);
      return Arrays.asList(permissions.clone());
    }

    return Collections.emptyList();
  }

  public static BusinessUnit getRequestBusinessUnit(Map<String, String> headers) {
    String buName = headers.get("business-unit-name");
    String bu = headers.get("business-unit");

    if (!StringUtils.isEmpty(bu)) {
      BusinessUnit businessUnit = new BusinessUnit();
      businessUnit.setId(bu);
      businessUnit.setName(buName);
      return businessUnit;
    }

    return null;
  }

  public static String getSubjectHeader(Map<String, String> headers) {
    return headers.get("x-subject");
  }
}
