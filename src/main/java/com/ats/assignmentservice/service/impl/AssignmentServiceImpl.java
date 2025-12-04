package com.ats.assignmentservice.service.impl;


import com.ats.assignmentservice.dto.AssignmentRequest;
import com.ats.assignmentservice.dto.AssignmentResponse;
import com.ats.assignmentservice.dto.ReturnAssignmentRequest;
import com.ats.assignmentservice.dto.UpdateAssetStatusRequest;
import com.ats.assignmentservice.entity.AssetStatus;
import com.ats.assignmentservice.entity.Assignment;
import com.ats.assignmentservice.entity.AssignmentStatus;
import com.ats.assignmentservice.feign.AssetServiceClient;
import com.ats.assignmentservice.feign.UserServiceClient;
import com.ats.assignmentservice.repository.AssignmentRepository;
import com.ats.assignmentservice.service.AssignmentService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final ModelMapper modelMapper;
    private final AssetServiceClient assetServiceClient;
    private final UserServiceClient useServiceClient;

    @Override
    public AssignmentResponse assignAsset(AssignmentRequest request, String authHeader) {

        // 1. Validate asset availability
        var asset = assetServiceClient.getAsset(request.getAssetId());
        if (asset.getStatus() != AssetStatus.AVAILABLE) {
            throw new RuntimeException("Asset is not available");
        }

        // 2. Validate employee is active
//        var employee = useServiceClient.getCurrentUser(authHeader);
//        if (!employee.isActive()) {
//            throw new RuntimeException("Employee is not active");
//        }

        // 3. Prevent duplicate active assignment
        Optional<Assignment> existing = assignmentRepository
                .findByAssetIdAndStatus(request.getAssetId(), AssignmentStatus.ASSIGNED);
        if (existing.isPresent()) {
            throw new RuntimeException("Asset is already assigned");
        }

        // 4. Create assignment
        Assignment assignment = Assignment.builder()
                .assetId(request.getAssetId())
                .employeeId(request.getEmployeeId())
                .assignedDate(request.getAssignedDate())
                .dueDate(request.getDueDate())
                .status(AssignmentStatus.ASSIGNED)
                .notes(request.getNotes())
                .build();

        Assignment saved = assignmentRepository.save(assignment);

        // 5. Update Asset Status
        UpdateAssetStatusRequest updateReq = new UpdateAssetStatusRequest();
        updateReq.setStatus("ASSIGNED");

        assetServiceClient.updateAsset(authHeader, request.getAssetId(), updateReq);

        return modelMapper.map(saved, AssignmentResponse.class);
    }

    @Override
    public AssignmentResponse returnAsset(Long assignmentId, ReturnAssignmentRequest request, String authHeader) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setReturnedDate(request.getReturnedDate());
        assignment.setStatus(AssignmentStatus.RETURNED);
        assignment.setCondition(request.getCondition());
        assignment.setNotes(request.getNotes());

        Assignment saved = assignmentRepository.save(assignment);

        // Update asset status
        UpdateAssetStatusRequest updateReq = new UpdateAssetStatusRequest();
        updateReq.setStatus(
                request.getCondition().equalsIgnoreCase("Good")
                        ? "AVAILABLE"
                        : "UNDER_MAINTENANCE"
        );

        assetServiceClient.updateAsset(authHeader, assignment.getAssetId(), updateReq);

        return modelMapper.map(saved, AssignmentResponse.class);
    }

    @Override
    public AssignmentResponse getAssignmentById(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        return modelMapper.map(assignment, AssignmentResponse.class);
    }

    @Override
    public Page<AssignmentResponse> listAssignments(Long employeeId, Long assetId, String status, boolean overdue, Pageable pageable) {

        LocalDate today = LocalDate.now();

        Page<Assignment> page;

        if (employeeId != null && assetId != null && status != null && overdue) {
            page = assignmentRepository.findByEmployeeIdAndAssetIdAndStatusAndDueDateBefore(
                    employeeId, assetId, AssignmentStatus.valueOf(status.toUpperCase()), today, pageable);
        } else if (employeeId != null && status != null && overdue) {
            page = assignmentRepository.findByEmployeeIdAndStatusAndDueDateBefore(
                    employeeId, AssignmentStatus.valueOf(status.toUpperCase()), today, pageable);
        } else if (employeeId != null && status != null) {
            page = assignmentRepository.findByEmployeeIdAndStatus(
                    employeeId, AssignmentStatus.valueOf(status.toUpperCase()), pageable);
        } else if (employeeId != null) {
            page = assignmentRepository.findByEmployeeId(employeeId, pageable);
        } else if (assetId != null) {
            page = assignmentRepository.findByAssetId(assetId, pageable);
        } else if (status != null) {
            page = assignmentRepository.findByStatus(AssignmentStatus.valueOf(status.toUpperCase()), pageable);
        } else if (overdue) {
            page = assignmentRepository.findByDueDateBeforeAndStatus(today, AssignmentStatus.ASSIGNED, pageable);
        } else {
            page = assignmentRepository.findAll(pageable);
        }

        return page.map(a -> modelMapper.map(a, AssignmentResponse.class));
    }

	@Override
	public AssignmentResponse getAssignmentByAssetId(Long assetId) {
		 Assignment assignment = assignmentRepository
	                .findByAssetIdAndStatus(assetId, AssignmentStatus.ASSIGNED) // fetch only currently assigned
	                .orElse(null);

	        if (assignment == null) {
	            return null; // or throw exception if preferred
	        
	}
	        return AssignmentResponse.builder()
	                .id(assignment.getId())
	                .assetId(assignment.getAssetId())
	                .employeeId(assignment.getEmployeeId())
	                .assignedDate(assignment.getAssignedDate())
	                .dueDate(assignment.getDueDate())
	                .status(assignment.getStatus().name())
	                .notes(assignment.getNotes())
	                .build();
}
}
