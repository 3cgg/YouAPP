package me.bunny.modular._p.streaming.coordinator.command;

import me.bunny.modular._p.streaming.coordinator.NodeStatus;

public class WorkflowErrorModel extends BaseCommandModel {

	/**
	 * the error message of workflow instance
	 * @see NodeStatus
	 */
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
