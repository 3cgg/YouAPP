package j.jave.framework.zookeeper.support;

import java.io.Serializable;

public interface JZooKeeperNodePath extends Serializable{
	
	public static final String SLASH="/";

	/**
	 * like "/root/category/type/instance"
	 * @return
	 */
	String getPath();
}
