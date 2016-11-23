package j.jave.kernal.streaming.zookeeper;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.streaming.JProperties;
import j.jave.kernal.streaming.zookeeper.JZooKeeperConnector.ZookeeperExecutor;

public class ZooKeeperExecutorGetter {

	public static ZookeeperExecutor getDefault(){
		JConfiguration configuration=JConfiguration.get();
		JZooKeeperConfig zooKeeperConfig=new JZooKeeperConfig();
		zooKeeperConfig.setConnectString(configuration.getString(JProperties.STREAMING_ZOOKEEPER_SERVER));
		zooKeeperConfig.setNamespace(configuration.getString(JProperties.STREAMING_ZOOKEEPER_NAMESPACE));
		ZookeeperExecutor executor=new JZooKeeperConnector(zooKeeperConfig)
				.connect();
		return executor;
	}
	
	
}
