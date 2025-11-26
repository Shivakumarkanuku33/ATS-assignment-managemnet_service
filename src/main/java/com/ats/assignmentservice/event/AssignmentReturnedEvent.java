package com.ats.assignmentservice.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AssignmentReturnedEvent extends ApplicationEvent {
	private final Long assignmentId;

	public AssignmentReturnedEvent(Object source, Long assignmentId) {
		super(source);
		this.assignmentId = assignmentId;
	}
}
