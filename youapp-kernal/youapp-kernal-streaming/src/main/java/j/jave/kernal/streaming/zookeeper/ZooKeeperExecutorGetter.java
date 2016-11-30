package j.jave.kernal.streaming.zookeeper;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.streaming.ConfigNames;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class ZooKeeperExecutorGetter {

	/**
	 * the common entrance to retrieve default zookeeper connection.
	 * @return
	 * @see ConfigNames#STREAMING_ZOOKEEPER_SERVER
	 * @see ConfigNames#STREAMING_ZOOKEEPER_NAMESPACE
	 */
	public static ZookeeperExecutor getDefault(){
		JConfiguration configuration=JConfiguration.get();
		ZooKeeperConfig zooKeeperConfig=new ZooKeeperConfig();
		zooKeeperConfig.setConnectString(configuration.getString(ConfigNames.STREAMING_ZOOKEEPER_SERVER));
		zooKeeperConfig.setNamespace(configuration.getString(ConfigNames.STREAMING_ZOOKEEPER_NAMESPACE));
		ZookeeperExecutor executor=new ZooKeeperConnector(zooKeeperConfig)
				.connect();
		return executor;
	}
	
	
}
