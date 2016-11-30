package test.j.jave.kernal.streaming.coordinator.b;

import java.util.Map;

import org.apache.kafka.common.utils.Utils;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.streaming.coordinator.NodeLeader;
import j.jave.kernal.streaming.coordinator.NodeWorkers;
import j.jave.kernal.streaming.netty.server.SimpleHttpNioChannelServer;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class MachineTwo {

	@SuppressWarnings({ "unused", "rawtypes"})
	public static void main(String[] args) throws Exception {
		
		JServiceFactoryManager.get().registerAllServices();
		SimpleHttpNioChannelServer channelServer =
				new SimpleHttpNioChannelServer(8081);
		try {
			channelServer.start();
		} catch (Exception e) {
			channelServer.close();
		}
		
		Map leaderConf=Machine.conf();

		ZookeeperExecutor executor=Machine.executor(leaderConf);
		
		NodeLeader nodeSelector=NodeLeader.startup("MAC-TWO",executor, leaderConf);
		
		NodeWorkers.startup(executor);
		Utils.sleep(3000);
		Machine.start(0, 3,leaderConf);
		Utils.sleep(10000);
	}
	
	
	
	
}
