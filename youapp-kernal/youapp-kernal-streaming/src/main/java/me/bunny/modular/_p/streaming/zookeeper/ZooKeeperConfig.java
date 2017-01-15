package me.bunny.modular._p.streaming.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.retry.ExponentialBackoffRetry;

import me.bunny.kernel._c.model.JModel;

public class ZooKeeperConfig implements JModel {

	private String connectString;
	
	private String namespace;
	
	private RetryPolicy retryPolicy=new ExponentialBackoffRetry(3000, 3);

	public String getConnectString() {
		return connectString;
	}

	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public RetryPolicy getRetryPolicy() {
		return retryPolicy;
	}

	public void setRetryPolicy(RetryPolicy retryPolicy) {
		this.retryPolicy = retryPolicy;
	}
	
}
