package com.trustpoint.cases.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Alert implements Serializable {
	private String id;
}