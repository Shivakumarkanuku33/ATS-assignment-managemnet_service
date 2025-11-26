package com.ats.assignmentservice.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AssignmentRequest {
    private Long assetId;
    private Long employeeId;
    private LocalDate assignedDate;
    private LocalDate dueDate;
    private String notes;
}
