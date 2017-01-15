package test.j.jave.kernal.streaming.coordinator.b;

import java.util.Map;

import org.apache.kafka.common.utils.Utils;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryManager;
import me.bunny.modular._p.streaming.ConfigNames;
import me.bunny.modular._p.streaming.coordinator.NodeLeader;
import me.bunny.modular._p.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

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
