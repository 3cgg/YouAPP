package j.jave.kernal.streaming.coordinator.command;

import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.streaming.coordinator.WorkerPathVal;

public class WorkerTemporary implements JModel {

	private WorkerPathVal workerPathVal;
	
	private String tempPath;

	public WorkerPathVal getWorkerPathVal() {
		return workerPathVal;
	}

	public void setWorkerPathVal(WorkerPathVal workerPathVal) {
		this.workerPathVal = workerPathVal;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
}
