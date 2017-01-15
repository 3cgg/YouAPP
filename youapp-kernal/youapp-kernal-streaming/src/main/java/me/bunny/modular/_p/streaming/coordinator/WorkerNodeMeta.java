package me.bunny.modular._p.streaming.coordinator;

public class WorkerNodeMeta extends NodeMeta {

	private int zkThreadCount;
	
	private int logThreadCount;

	private int heartBeatTimeMs;

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

	public int getHeartBeatTimeMs() {
		return heartBeatTimeMs;
	}

	public void setHeartBeatTimeMs(int heartBeatTimeMs) {
		this.heartBeatTimeMs = heartBeatTimeMs;
	}
	
	
}
