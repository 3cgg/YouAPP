package test.j.jave.kernal.streaming.coordinator;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.utils.Utils;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.streaming.coordinator.NodeSelector;
import j.jave.kernal.streaming.coordinator.NodeWorker;
import j.jave.kernal.streaming.coordinator.NodeWorkers;
import j.jave.kernal.streaming.coordinator.Workflow;
import j.jave.kernal.streaming.coordinator.WorkflowMeta;
import j.jave.kernal.streaming.kafka.JKafkaProducerConfig;
import j.jave.kernal.streaming.kafka.KafkaNameKeys;
import j.jave.kernal.streaming.zookeeper.JZooKeeperConfig;
import j.jave.kernal.streaming.zookeeper.JZooKeeperConnecter;
import j.jave.kernal.streaming.zookeeper.JZooKeeperConnecter.ZookeeperExecutor;

public class DistrLockTest {

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public static void main(String[] args) {
		
		Map leaderConf=new HashMap<>();
		KafkaNameKeys.setKafkaServer(leaderConf, "192.168.0.97:9092");
		leaderConf.putAll(JKafkaProducerConfig.def());
		
		JZooKeeperConfig zooKeeperConfig=new JZooKeeperConfig();
		zooKeeperConfig.setConnectString("nim1.storm.com:2182,nim2.storm.com");
		zooKeeperConfig.setNamespace("test-a");
		ZookeeperExecutor executor=new JZooKeeperConnecter(zooKeeperConfig)
				.connect();
		
		NodeSelector nodeSelector=NodeSelector.startup(executor, leaderConf);
		
		WorkflowMeta workflowMeta=WorkflowMetaDemoTest.get();
		Workflow workflow=new Workflow(workflowMeta.getName());
		workflow.setNodeData(workflowMeta.getNodeData());
		nodeSelector.addWorkflow(workflow);
		
		NodeWorkers.startup(executor);
		Utils.sleep(3000);
		start(0, 3,leaderConf);
		Utils.sleep(10000);
	}
	
	public static void start(int start,int end,Map conf){
		for(int i=start;i<(end+1);i++){
			final int _i=i;
			new Thread(new Runnable() {
				
				@Override
				public void run() {

					while(true){
						NodeWorker nodeWorker=NodeWorkers.get(_i, "name-"+_i
								, WorkflowMetaDemoTest.get(), conf);
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
