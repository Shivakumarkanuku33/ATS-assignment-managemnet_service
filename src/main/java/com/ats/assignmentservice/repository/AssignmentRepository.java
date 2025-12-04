package com.ats.assignmentservice.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ats.assignmentservice.entity.Assignment;
import com.ats.assignmentservice.entity.AssignmentStatus;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

	

    Page<Assignment> findByEmployeeId(Long employeeId, Pageable pageable);

    Page<Assignment> findByAssetId(Long assetId, Pageable pageable);

    Page<Assignment> findByStatus(AssignmentStatus status, Pageable pageable);

    Page<Assignment> findByEmployeeIdAndStatus(Long employeeId, AssignmentStatus status, Pageable pageable);

    Page<Assignment> findByEmployeeIdAndStatusAndDueDateBefore(Long employeeId, AssignmentStatus status, LocalDate dueDate, Pageable pageable);

    Page<Assignment> findByEmployeeIdAndAssetIdAndStatusAndDueDateBefore(Long employeeId, Long assetId, AssignmentStatus status, LocalDate dueDate, Pageable pageable);

    Page<Assignment> findByDueDateBeforeAndStatus(LocalDate dueDate, AssignmentStatus status, Pageable pageable);
    
//    Optional<Assignment> findByAssetIdAndStatus(Long assetId, String status);
    
    Optional<Assignment> findByAssetIdAndStatus(Long assetId, AssignmentStatus status);
    
}
