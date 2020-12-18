package com.trustpoint.cases.dto;
import lombok.Data;

import java.util.List;

@Data
public class AlertCaseRequest {
  private String caseId;
  private String alertId;
  private String customerId;
  private String owner;
  // customer's business unit.
  private String businessUnit;
  private int alertScore;
  private String caseType;
  private String title;
  private List<AlertNote> notes;
  private List<AlertAttachment> attachments;
}
