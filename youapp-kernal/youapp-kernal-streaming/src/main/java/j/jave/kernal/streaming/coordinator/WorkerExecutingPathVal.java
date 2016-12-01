package j.jave.kernal.streaming.coordinator;

import j.jave.kernal.jave.model.JModel;

public class WorkerExecutingPathVal implements JModel {

	private WorkerPathVal workerPathVal;
	
	/**
	 * created worker path per workflow instance calling
	 */
	private String workerExecutingPath;

	public WorkerPathVal getWorkerPathVal() {
		return workerPathVal;
	}

	public void setWorkerPathVal(WorkerPathVal workerPathVal) {
		this.workerPathVal = workerPathVal;
	}

	public String getWorkerExecutingPath() {
		return workerExecutingPath;
	}

	public void setWorkerExecutingPath(String workerExecutingPath) {
		this.workerExecutingPath = workerExecutingPath;
	}
	
}
