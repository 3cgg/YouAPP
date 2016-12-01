package test.j.jave.kernal.streaming.coordinator.b;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.kafka.common.utils.Utils;

import j.jave.kernal.streaming.coordinator.NodeWorker;
import j.jave.kernal.streaming.coordinator.NodeWorkers;
import j.jave.kernal.streaming.coordinator.WorkerConfigNames;
import j.jave.kernal.streaming.kafka.KafkaNameKeys;
import j.jave.kernal.streaming.kafka.KafkaProducerConfig;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import j.jave.kernal.streaming.zookeeper.ZooKeeperExecutorGetter;

public class Machine {
	
	private static Random random=new Random();
	
	public static Map conf(){
		Map leaderConf=new HashMap<>();
		KafkaNameKeys.setKafkaServer(leaderConf, "192.168.0.97:9092");
		leaderConf.putAll(KafkaProducerConfig.def());
		return leaderConf;
	}
	
	public static ZookeeperExecutor executor(Map conf){
		return ZooKeeperExecutorGetter.getDefault();
	}

	public static void start(int start,int end){
		for(int i=start;i<(end+1);i++){
			final int _i=i;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Map conf=Machine.conf();
					int port=random.nextInt(9000-8080)+8080+_i;
					System.out.println("port:"+port);
					conf.put(WorkerConfigNames.WORKER_NETTY_PORT, port);
					NodeWorker nodeWorker=NodeWorkers.get(_i, "name-"+_i
							, WorkflowMetaDemoTest.get(), conf);
					while(true){
						try{
							nodeWorker.acquire();
							System.out.println(nodeWorker.getId()+ " get lock , wait some time.");
							Utils.sleep(10000);
						}catch (Exception e) {
							e.printStackTrace();
						}finally {
							try {
								nodeWorker.release();
								System.out.println(nodeWorker.getId()+ " release lock");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					
				}
			},"name - "+i).start();
		}
	}
	
}
