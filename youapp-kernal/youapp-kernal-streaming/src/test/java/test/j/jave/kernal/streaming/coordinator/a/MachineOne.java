package test.j.jave.kernal.streaming.coordinator.a;

import java.util.Map;

import org.apache.kafka.common.utils.Utils;

import j.jave.kernal.streaming.coordinator.NodeLeader;
import j.jave.kernal.streaming.coordinator.NodeWorkers;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class MachineOne {

	@SuppressWarnings({ "unused", "rawtypes"})
	public static void main(String[] args) {
		
		Map leaderConf=Machine.conf();

		ZookeeperExecutor executor=Machine.executor(leaderConf);
		
		NodeLeader nodeSelector=NodeLeader.startup("MAC-ONE",executor, leaderConf);
		
		NodeWorkers.startup(executor);
		Utils.sleep(3000);
		Machine.start(0, 1,leaderConf);
		Utils.sleep(10000);
	}
	
	
	
	
}
