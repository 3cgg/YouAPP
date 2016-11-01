package j.jave.kernal.streaming.coordinator;

import j.jave.kernal.streaming.zookeeper.JZooKeeperConnector.ZookeeperExecutor;

public class CommandResource {

	private ZookeeperExecutor executor;
	
	public ZookeeperExecutor getExecutor() {
		return executor;
	}
	
	void setExecutor(ZookeeperExecutor executor) {
		this.executor = executor;
	}
	
	
}
