package test.j.jave.kernal.streaming.coordinator.b;

import java.io.IOException;
import java.util.Map;

import org.apache.kafka.common.utils.Utils;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.streaming.coordinator.NodeLeader;
import j.jave.kernal.streaming.coordinator.NodeWorkers;
import j.jave.kernal.streaming.netty.server.SimpleHttpNioChannelServer;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class MachineThree {

	@SuppressWarnings({ "unused", "rawtypes"})
	public static void main(String[] args) throws Exception {
		
		JServiceFactoryManager.get().registerAllServices();
		SimpleHttpNioChannelServer channelServer =
				new SimpleHttpNioChannelServer(8082);
		try {
			channelServer.start();
		} catch (Exception e) {
			channelServer.close();
		}
		
		Map leaderConf=Machine.conf();

		ZookeeperExecutor executor=Machine.executor(leaderConf);
		
		NodeLeader nodeSelector=NodeLeader.startup("MAC-THREE",executor, leaderConf);
		
		NodeWorkers.startup(executor);
		Utils.sleep(3000);
		Machine.start(0, 10,leaderConf);
		Utils.sleep(10000);
	}
	
	
	
	
}
