package com.trustpoint.cases.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trustpoint.cases.model.File;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AlertAttachment {
	@Id
	@GeneratedValue
	private UUID id;
	private File file;
	private String comment;
	private String uploadedBy;
}
