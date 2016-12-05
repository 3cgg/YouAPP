package j.jave.kernal.streaming.coordinator;

public class WorkerNodeMeta extends NodeMeta {

	private int zkThreadCount;
	
	private int logThreadCount;

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
