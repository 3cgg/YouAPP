package test.j.jave.kernal.streaming.coordinator.b;

import org.apache.kafka.common.utils.Utils;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.streaming.coordinator.NodeWorkers;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class MachineWorkerThree {

	@SuppressWarnings({ "unused", "rawtypes"})
	public static void main(String[] args) throws Exception {
		
		JServiceFactoryManager.get().registerAllServices();

		ZookeeperExecutor executor=Machine.executor(Machine.conf());
		
		NodeWorkers.startup(executor);
		Utils.sleep(3000);
		Machine.start(0, 10);
		Utils.sleep(10000);
	}
	
	
	
	
}
