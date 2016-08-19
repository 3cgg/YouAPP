package j.jave.kernal.zookeeper;

import java.io.Serializable;

public interface JZooKeeperNodePath extends Serializable{
	
	public static final String SLASH="/";

	/**
	 * like "/root/category/type/instance"
	 * @return
	 */
	String getPath();
}
