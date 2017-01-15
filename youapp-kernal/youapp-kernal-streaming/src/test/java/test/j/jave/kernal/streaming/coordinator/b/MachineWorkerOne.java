package test.j.jave.kernal.streaming.coordinator.b;

import org.apache.kafka.common.utils.Utils;

import j.jave.kernal.streaming.coordinator.NodeWorkers;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryManager;

public class MachineWorkerOne {

	@SuppressWarnings({ "unused", "rawtypes"})
	public static void main(String[] args) throws Exception {
		
		JServiceFactoryManager.get().registerAllServices();

		ZookeeperExecutor executor=Machine.executor(Machine.conf());
		
		NodeWorkers.startup(executor);
		Utils.sleep(3000);
		Machine.start(1, 1);
		Utils.sleep(10000);
	}
	
	
	
	
}
