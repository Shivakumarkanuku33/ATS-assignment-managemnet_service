package com.ats.assignmentservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ReturnAssignmentRequest {
	@NotNull
	private LocalDate returnedDate;
	private String condition;
	private String notes;
}