package j.jave.kernal.streaming.coordinator;

import j.jave.kernal.jave.model.JModel;

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
	 * the record tieme
	 */
	private long time;

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

	
}