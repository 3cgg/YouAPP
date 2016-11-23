package j.jave.kernal.streaming.zookeeper;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.streaming.JProperties;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class ZooKeeperExecutorGetter {

	public static ZookeeperExecutor getDefault(){
		JConfiguration configuration=JConfiguration.get();
		ZooKeeperConfig zooKeeperConfig=new ZooKeeperConfig();
		zooKeeperConfig.setConnectString(configuration.getString(JProperties.STREAMING_ZOOKEEPER_SERVER));
		zooKeeperConfig.setNamespace(configuration.getString(JProperties.STREAMING_ZOOKEEPER_NAMESPACE));
		ZookeeperExecutor executor=new ZooKeeperConnector(zooKeeperConfig)
				.connect();
		return executor;
	}
	
	
}
