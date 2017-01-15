package j.jave.kernal.streaming.coordinator;

import me.bunny.kernel.jave.model.JModel;

public class WorkerExecutingPathVal implements JModel {

	private WorkerPathVal workerPathVal;
	
	/**
	 * created worker path per workflow instance calling
	 */
	private String workerExecutingPath;
	
	/**
	 * created worker path per workflow instance calling
	 */
	private String workerExecutingErrorPath;

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

	public String getWorkerExecutingErrorPath() {
		return workerExecutingErrorPath;
	}

	public void setWorkerExecutingErrorPath(String workerExecutingErrorPath) {
		this.workerExecutingErrorPath = workerExecutingErrorPath;
	}
	
}
