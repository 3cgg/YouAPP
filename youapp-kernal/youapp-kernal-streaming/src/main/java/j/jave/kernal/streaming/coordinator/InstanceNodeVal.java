package j.jave.kernal.streaming.coordinator;

import j.jave.kernal.jave.model.JModel;

public class InstanceNodeVal implements JModel,IParallel {

	/**
	 * 1 is parrallel, otherwise 0
	 */
	private String parallel;
	
	/**
	 * the worker id , that may be a virtual /  real worker 
	 */
	private int id;
	
	/**
	 * the instance id
	 */
	private long sequence;
	
	/**
	 * the running status , {@link NodeStatus}
	 */
	private NodeStatus status;
	
	/**
	 * the record time
	 */
	private long time;

	
	public String getParallel() {
		return parallel;
	}

	public void setParallel(String parallel) {
		this.parallel = parallel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public NodeStatus getStatus() {
		return status;
	}

	public void setStatus(NodeStatus status) {
		this.status = status;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public boolean isParallel(){
		return IParallel._TRUE.equals(parallel);
	}
}
