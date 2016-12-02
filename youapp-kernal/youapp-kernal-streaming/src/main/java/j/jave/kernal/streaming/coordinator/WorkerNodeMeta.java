package j.jave.kernal.streaming.coordinator;

public class WorkerNodeMeta extends NodeMeta {

	private int zkThreadCount;
	
	private int logThreadCount;
	
	private boolean leader=false;

	public boolean isLeader() {
		return leader;
	}
	
	public void setLeader(boolean leader) {
		this.leader = leader;
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
	
}
