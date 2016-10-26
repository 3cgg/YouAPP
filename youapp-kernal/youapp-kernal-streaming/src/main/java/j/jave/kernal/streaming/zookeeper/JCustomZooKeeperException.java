package j.jave.kernal.streaming.zookeeper;

import j.jave.kernal.jave.model.JModel;

@SuppressWarnings("serial")
public class JCustomZooKeeperException extends RuntimeException implements JModel{
	
	public JCustomZooKeeperException(Exception e) {
		super(e);
	}
	
	public JCustomZooKeeperException(String message,Exception e) {
		super(message,e);
	}
	
	public JCustomZooKeeperException(String message) {
		super(message);
	}
}