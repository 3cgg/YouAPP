package test.j.jave.kernal.streaming.coordinator.b;

import java.util.Map;

import org.apache.kafka.common.utils.Utils;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.streaming.ConfigNames;
import j.jave.kernal.streaming.coordinator.NodeLeader;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class MachineLeaderOne {

	@SuppressWarnings({ "unused", "rawtypes"})
	public static void main(String[] args) throws Exception {
		
		JServiceFactoryManager.get().registerAllServices();
		
		Map leaderConf=Machine.conf();

		leaderConf.put(ConfigNames.STREAMING_NODE_ID, 1);
		leaderConf.put(ConfigNames.STREAMING_NODE_NAME, "MAC-ONE");
		leaderConf.put(ConfigNames.STREAMING_LEADER_NETTY_SERVER_PORT, 8080);
		ZookeeperExecutor executor=Machine.executor(leaderConf);
		
		NodeLeader nodeSelector=NodeLeader.startup(executor, leaderConf);
		
		Utils.sleep(10000);
	}
	
	
	
	
}
