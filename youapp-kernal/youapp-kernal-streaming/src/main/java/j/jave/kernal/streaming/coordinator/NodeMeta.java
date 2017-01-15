package j.jave.kernal.streaming.coordinator;

import me.bunny.kernel._c.model.JModel;

public class NodeMeta implements JModel {
	
	/**
	 * the node id
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
	
	/**
	 * the http port
	 */
	private int port;

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

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
