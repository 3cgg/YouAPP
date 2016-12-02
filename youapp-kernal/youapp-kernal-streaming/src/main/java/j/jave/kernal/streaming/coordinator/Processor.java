package j.jave.kernal.streaming.coordinator;

import j.jave.kernal.jave.model.JModel;

/**
 * the processor meta data info
 * @author JIAZJ
 *
 */
public class Processor implements JModel{

	private String tempPath;
	
	private WorkerNodeMeta workerNodeMeta;

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

	public WorkerNodeMeta getWorkerNodeMeta() {
		return workerNodeMeta;
	}

	public void setWorkerNodeMeta(WorkerNodeMeta workerNodeMeta) {
		this.workerNodeMeta = workerNodeMeta;
	}
	
	
	
}
