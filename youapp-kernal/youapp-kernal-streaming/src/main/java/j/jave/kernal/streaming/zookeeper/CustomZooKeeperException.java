package j.jave.kernal.streaming.zookeeper;

import me.bunny.kernel.jave.model.JModel;

@SuppressWarnings("serial")
public class CustomZooKeeperException extends RuntimeException implements JModel{
	
	public CustomZooKeeperException(Exception e) {
		super(e);
	}
	
	public CustomZooKeeperException(String message,Exception e) {
		super(message,e);
	}
	
	public CustomZooKeeperException(String message) {
		super(message);
	}
}