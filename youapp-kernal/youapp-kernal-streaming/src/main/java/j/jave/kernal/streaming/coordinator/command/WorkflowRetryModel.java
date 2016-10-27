package j.jave.kernal.streaming.coordinator.command;

import j.jave.kernal.streaming.coordinator.NodeData.NodeStatus;

public class WorkflowRetryModel extends BaseCommandModel {

	/**
	 * how many time the workflow can be triggered.
	 * @see NodeStatus
	 */
	private final long maxCount;
	
	/**
	 * how many the workflow is already triggered.
	 */
	private long count;
	
	public WorkflowRetryModel(long maxCount) {
		this.maxCount = maxCount;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getMaxCount() {
		return maxCount;
	}
		
}
