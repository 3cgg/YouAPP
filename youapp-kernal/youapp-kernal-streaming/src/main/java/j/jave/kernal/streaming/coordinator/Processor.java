package j.jave.kernal.streaming.coordinator;

import me.bunny.kernel.jave.model.JModel;

/**
 * the processor meta data info
 * @author JIAZJ
 *
 */
public class Processor implements JModel{

	private String nodePath;
	
	private WorkerNodeMeta workerNodeMeta;

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public WorkerNodeMeta getWorkerNodeMeta() {
		return workerNodeMeta;
	}

	public void setWorkerNodeMeta(WorkerNodeMeta workerNodeMeta) {
		this.workerNodeMeta = workerNodeMeta;
	}
	
	
	
}
