package test.j.jave.kernal.streaming.coordinator.b;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.kafka.common.utils.Utils;

import me.bunny.kernel._c.json.JJSON;
import me.bunny.modular._p.streaming.ConfigNames;
import me.bunny.modular._p.streaming.coordinator.NodeWorker;
import me.bunny.modular._p.streaming.coordinator.NodeWorkers;
import me.bunny.modular._p.streaming.kafka.KafkaNameKeys;
import me.bunny.modular._p.streaming.kafka.KafkaProducerConfig;
import me.bunny.modular._p.streaming.zookeeper.ZooKeeperExecutorGetter;
import me.bunny.modular._p.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class Machine {
	
	private static Random random=new Random();
	
	private static long count=0;
	
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
					conf.put(ConfigNames.STREAMING_WORKER_NETTY_SERVER_PORT, port);
					NodeWorker nodeWorker=NodeWorkers.get(_i, "name-"+_i
							, WorkflowMetaDemoTest.get(), conf);
					while(true){
						try{
							System.out.println(nodeWorker.getId()+ "=== ready to get lock .");
							nodeWorker.acquire();
							System.out.println("worker["+nodeWorker.getId()+ "]; param : ----- "
									+JJSON.get().formatObject(nodeWorker.getWorkflowConf())
									+"-----; ===got lock , wait some executing time .");
							count++;
							Utils.sleep(3000);
						}catch (Exception e) {
							e.printStackTrace();
						}finally {
							try {
								if(count%10==0){
									nodeWorker.release(new RuntimeException("test error["+count+"]..."));
								}else{
									nodeWorker.release();
								}
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
