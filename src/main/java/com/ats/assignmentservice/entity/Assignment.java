package com.ats.assignmentservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long assetId;
	private Long employeeId;

	private LocalDate assignedDate;
	private LocalDate dueDate;
	private LocalDate returnedDate;

	private String assignedBy;

//	@Column(length = 1000)
	private String notes;

	@Enumerated(EnumType.STRING)
	private AssignmentStatus status;
	
	private String condition;
}
