package me.bunny.modular._p.streaming.coordinator;

import me.bunny.modular._p.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class CommandResource {

	private ZookeeperExecutor executor;
	
	public ZookeeperExecutor getExecutor() {
		return executor;
	}
	
	void setExecutor(ZookeeperExecutor executor) {
		this.executor = executor;
	}
	
	
}
