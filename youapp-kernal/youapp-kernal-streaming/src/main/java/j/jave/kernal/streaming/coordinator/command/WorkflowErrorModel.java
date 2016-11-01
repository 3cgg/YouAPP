package j.jave.kernal.streaming.coordinator.command;

import j.jave.kernal.streaming.coordinator.NodeData.NodeStatus;

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
