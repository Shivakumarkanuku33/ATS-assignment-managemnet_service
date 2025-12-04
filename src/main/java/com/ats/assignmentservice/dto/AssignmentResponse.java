package com.ats.assignmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonPropertyOrder({
    "id",
    "assetId",
    "employeeId",
    "assignedDate",
    "dueDate",
    "status",
    "notes"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponse {
	private Long id;
	private Long assetId;
	private Long employeeId;
	private LocalDate assignedDate;
	private LocalDate dueDate;
//	private LocalDate returnedDate;
	private String status;
	private String notes;
//	private String condition;
}