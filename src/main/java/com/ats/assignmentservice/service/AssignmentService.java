package com.ats.assignmentservice.service;

import com.ats.assignmentservice.dto.AssignmentRequest;
import com.ats.assignmentservice.dto.AssignmentResponse;
import com.ats.assignmentservice.dto.ReturnAssignmentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssignmentService {

	AssignmentResponse assignAsset(AssignmentRequest request, String assignedBy);

	AssignmentResponse returnAsset(Long assignmentId, ReturnAssignmentRequest request, String returnedBy);

	AssignmentResponse getAssignmentById(Long assignmentId);

//	Page<AssignmentResponse> listAssignments(Long employeeId, Long assetId, String status, Pageable pageable);

	Page<AssignmentResponse> listAssignments(Long employeeId, Long assetId, String status, boolean overdue,
			Pageable pageable);
	
	public AssignmentResponse getAssignmentByAssetId(Long assetId);
}