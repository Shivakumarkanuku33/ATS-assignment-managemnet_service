package com.ats.assignmentservice.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AssignmentCreatedEvent extends ApplicationEvent {
	private final Long assignmentId;

	public AssignmentCreatedEvent(Object source, Long assignmentId) {
		super(source);
		this.assignmentId = assignmentId;
	}
}
