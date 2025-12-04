package com.ats.assignmentservice.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.ats.assignmentservice.dto.AssignmentRequest;
import com.ats.assignmentservice.dto.AssignmentResponse;
import com.ats.assignmentservice.dto.ReturnAssignmentRequest;
import com.ats.assignmentservice.service.impl.AssignmentServiceImpl;


@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentServiceImpl assignmentService;

    @PostMapping("/create")
    public ResponseEntity<AssignmentResponse> assignAsset(
            @RequestBody AssignmentRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        String token = "Bearer " + jwt.getTokenValue();

        return ResponseEntity.status(201)
                .body(assignmentService.assignAsset(request, token));
    }

    @PreAuthorize("hasAnyRole('ASSET_MANAGER','ADMIN')")
    @PutMapping("/{id}/return")
    public ResponseEntity<AssignmentResponse> returnAsset(
            @PathVariable Long id,
            @RequestBody ReturnAssignmentRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        String authHeader = "Bearer " + jwt.getTokenValue(); // FIXED

        return ResponseEntity.ok(
                assignmentService.returnAsset(id, request, authHeader)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> listAssignments(
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) Long assetId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false, defaultValue = "false") boolean overdue,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "assignedDate,desc") String sort) {

        String[] sortParams = sort.split(",");
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0])
        );

        Page<AssignmentResponse> assignmentPage = assignmentService.listAssignments(
                employeeId, assetId, status, overdue, pageable);

        // Prepare meta
        var meta = new java.util.HashMap<String, Object>();
        meta.put("page", assignmentPage.getNumber());
        meta.put("size", assignmentPage.getSize());
        meta.put("totalElements", assignmentPage.getTotalElements());
        meta.put("totalPages", assignmentPage.getTotalPages());

        var response = new java.util.HashMap<String, Object>();
        response.put("status", "success");
        response.put("data", assignmentPage.getContent());
        response.put("meta", meta);

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/asset/{assetId}")
    public ResponseEntity<AssignmentResponse> getAssignmentByAssetId(@PathVariable Long assetId) {
        AssignmentResponse assignment = assignmentService.getAssignmentByAssetId(assetId);
        return ResponseEntity.ok(assignment);
    }
}
