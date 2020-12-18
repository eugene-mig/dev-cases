package com.trustpoint.cases.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.trustpoint.cases.model.Note;
import com.trustpoint.cases.values.CaseType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CasePayload {
  @NotBlank(message = "Case name cannot be empty")
  private String name;
  @NotNull(message = "Case type cannot be empty")
  private CaseType type;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(pattern="M/d/yyyy HH:mm:ss")
  private LocalDateTime openingDate;

  private String description = "";
  private String internalReferenceCode = "";

  private String relatedCaseID = "";
  private String customer;

  private List<Note> notes = new ArrayList<>();
  private List<Alert> alerts = new ArrayList<>();
}
