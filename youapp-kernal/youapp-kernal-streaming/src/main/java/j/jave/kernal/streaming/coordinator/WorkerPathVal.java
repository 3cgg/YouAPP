package j.jave.kernal.streaming.coordinator;

import java.util.Map;

import me.bunny.kernel.jave.model.JModel;

/**
 * SEE values on the {@link Workflow#getPluginWorkersPath()}s
 * @author JIAZJ
 *
 */
@SuppressWarnings("serial")
public class WorkerPathVal implements JModel {
	
	/**
	 * the worker id
	 */
	private int id;
	
	/**
	 * the instance id
	 */
	private long sequence;
	
	/**
	 * the related instance path
	 */
	private String instancePath;
	
	/**
	 * the workflow parameters/configuration
	 */
	private Map<String, Object> conf; 
	
	/**
	 * the record tieme
	 */
	private long time;
	
	/**
	 * the instance node status
	 */
	private NodeStatus nodeStatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInstancePath() {
		return instancePath;
	}

	public void setInstancePath(String instancePath) {
		this.instancePath = instancePath;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Map<String, Object> getConf() {
		return conf;
	}

	public void setConf(Map<String, Object> conf) {
		this.conf = conf;
	}

	public NodeStatus getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(NodeStatus nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	
}
