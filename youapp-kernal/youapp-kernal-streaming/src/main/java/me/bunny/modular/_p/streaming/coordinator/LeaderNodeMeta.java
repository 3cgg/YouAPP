package me.bunny.modular._p.streaming.coordinator;

public class LeaderNodeMeta extends NodeMeta {

	private int zkThreadCount;
	
	private int logThreadCount;
	
	private String taskRepoPath;
	
	private int workflowStatusMs;
	
	private int workflowToOnlineMs;
	
	private boolean leader=false;

	public boolean isLeader() {
		return leader;
	}
	
	/**
	 * leader RPC port
	 */
	@Override
	public int getPort() {
		return super.getPort();
	}

	public int getZkThreadCount() {
		return zkThreadCount;
	}

	public void setZkThreadCount(int zkThreadCount) {
		this.zkThreadCount = zkThreadCount;
	}

	public int getLogThreadCount() {
		return logThreadCount;
	}

	public void setLogThreadCount(int logThreadCount) {
		this.logThreadCount = logThreadCount;
	}

	public String getTaskRepoPath() {
		return taskRepoPath;
	}

	public void setTaskRepoPath(String taskRepoPath) {
		this.taskRepoPath = taskRepoPath;
	}

	public int getWorkflowStatusMs() {
		return workflowStatusMs;
	}

	public void setWorkflowStatusMs(int workflowStatusMs) {
		this.workflowStatusMs = workflowStatusMs;
	}

	public int getWorkflowToOnlineMs() {
		return workflowToOnlineMs;
	}

	public void setWorkflowToOnlineMs(int workflowToOnlineMs) {
		this.workflowToOnlineMs = workflowToOnlineMs;
	}
	
	public void setLeader(boolean leader) {
		this.leader = leader;
	}
	
}
