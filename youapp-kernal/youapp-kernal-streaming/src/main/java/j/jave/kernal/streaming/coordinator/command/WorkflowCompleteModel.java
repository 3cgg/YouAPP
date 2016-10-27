package j.jave.kernal.streaming.coordinator.command;

import j.jave.kernal.streaming.coordinator.NodeData.NodeStatus;

public class WorkflowCompleteModel extends BaseCommandModel {

	/**
	 * the status of workflow instance , maybe any one of {@link NodeStatus}
	 * @see NodeStatus
	 */
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
