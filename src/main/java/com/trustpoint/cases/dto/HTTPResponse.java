package com.trustpoint.cases.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class HTTPResponse {
  private HttpStatus status;
  private String message;
  private Object body;
}
