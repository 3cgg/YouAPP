package j.jave.kernal.streaming.coordinator.rpc.leader;

import me.bunny.kernel.jave.model.JModel;

public class ExecutingWorker implements JModel {

	private int workerId;
	
	/**
	 * the workflow name the worker belongs to
	 */
	private String workflowName;
	
	/**
	 * the instance sequence.
	 */
	private long sequence;
	
	private String workflowInstancePath;
	
	private String workerInstancePath;
	
	private long time;
	
	private String timeStr;
	
	

	public int getWorkerId() {
		return workerId;
	}

	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public String getWorkflowInstancePath() {
		return workflowInstancePath;
	}

	public void setWorkflowInstancePath(String workflowInstancePath) {
		this.workflowInstancePath = workflowInstancePath;
	}

	public String getWorkerInstancePath() {
		return workerInstancePath;
	}

	public void setWorkerInstancePath(String workerInstancePath) {
		this.workerInstancePath = workerInstancePath;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	
}
