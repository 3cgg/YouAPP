package j.jave.kernal.streaming.coordinator;

public class LeaderNodeMeta extends NodeMeta {

	private int zkThreadCount;
	
	private int logThreadCount;
	
	private final boolean leader=true;

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
	
	
	
}
