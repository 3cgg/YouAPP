package me.bunny.modular._p.streaming.coordinator.command;

import me.bunny.kernel._c.model.JModel;
import me.bunny.modular._p.streaming.coordinator.WorkerExecutingPathVal;

public class WorkerTemporary implements JModel {

	private WorkerExecutingPathVal workerExecutingPathVal;
	
	private String tempPath;

	public WorkerExecutingPathVal getWorkerExecutingPathVal() {
		return workerExecutingPathVal;
	}

	public void setWorkerExecutingPathVal(WorkerExecutingPathVal workerExecutingPathVal) {
		this.workerExecutingPathVal = workerExecutingPathVal;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
}
