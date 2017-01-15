package test.j.jave.kernal.streaming.coordinator.b;

import java.util.Map;

import org.apache.kafka.common.utils.Utils;

import j.jave.kernal.streaming.ConfigNames;
import j.jave.kernal.streaming.coordinator.NodeLeader;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryManager;

public class MachineLeaderTwo {

	@SuppressWarnings({ "unused", "rawtypes"})
	public static void main(String[] args) throws Exception {
		
		JServiceFactoryManager.get().registerAllServices();
		
		Map leaderConf=Machine.conf();
		leaderConf.put(ConfigNames.STREAMING_NODE_ID, 2);
		leaderConf.put(ConfigNames.STREAMING_NODE_NAME, "MAC-TWO");
		leaderConf.put(ConfigNames.STREAMING_LEADER_NETTY_SERVER_PORT, 8081);
		ZookeeperExecutor executor=Machine.executor(leaderConf);
		
		NodeLeader nodeSelector=NodeLeader.startup(executor, leaderConf);
		
		Utils.sleep(10000);
	}
	
	
	
	
}
