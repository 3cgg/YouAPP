package test.j.jave.kernal.streaming.coordinator.b;

import org.apache.kafka.common.utils.Utils;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryManager;
import me.bunny.modular._p.streaming.coordinator.NodeWorkers;
import me.bunny.modular._p.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class MachineWorkerTwo {

	@SuppressWarnings({ "unused", "rawtypes"})
	public static void main(String[] args) throws Exception {
		
		JServiceFactoryManager.get().registerAllServices();

		ZookeeperExecutor executor=Machine.executor(Machine.conf());
		
		NodeWorkers.startup(executor);
		Utils.sleep(3000);
		Machine.start(0, 3);
		Utils.sleep(10000);
	}
	
	
	
	
}
