package j.jave.kernal.streaming.coordinator;

import j.jave.kernal.jave.model.JModel;

public abstract class NodeMeta implements JModel {
	
	/**
	 * the leader id
	 */
	private int id;
	
	/**
	 * the node name
	 */
	private String name;
	
	/**
	 * the node machine host
	 */
	private String host;
	
	/**
	 * the node processor id
	 */
	private int pid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
	
	
	
	
}
