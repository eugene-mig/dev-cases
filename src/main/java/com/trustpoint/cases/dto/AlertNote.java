package com.trustpoint.cases.dto;

import lombok.Data;
import javax.persistence.*;

@Data
public class AlertNote {
  @Column(name = "body", columnDefinition = "text")
  private String body;

  @Column(name="added_by", columnDefinition = "varchar(100)")
  private String addedBy;
}
