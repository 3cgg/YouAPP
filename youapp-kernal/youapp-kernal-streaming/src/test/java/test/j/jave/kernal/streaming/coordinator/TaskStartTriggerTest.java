package test.j.jave.kernal.streaming.coordinator;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.streaming.coordinator.CoordinatorPaths;
import j.jave.kernal.streaming.coordinator.WorkflowMeta;
import j.jave.kernal.streaming.zookeeper.JZooKeeperConfig;
import j.jave.kernal.streaming.zookeeper.JZooKeeperConnecter;
import j.jave.kernal.streaming.zookeeper.JZooKeeperConnecter.ZookeeperExecutor;

public class TaskStartTriggerTest {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		
		JZooKeeperConfig zooKeeperConfig=new JZooKeeperConfig();
		zooKeeperConfig.setConnectString("nim1.storm.com:2182,nim2.storm.com");
		zooKeeperConfig.setNamespace("test-a");
		ZookeeperExecutor executor=new JZooKeeperConnecter(zooKeeperConfig)
				.connect();
		
		WorkflowMeta demo=WorkflowMetaDemoTest.get();
		//basePath+"/workflow-trigger/"+workflow.getName();
		executor.setPath(CoordinatorPaths.BASE_PATH
				+"/workflow-trigger",JJSON.get().formatObject(demo));
		Thread.currentThread().sleep(2000);
		
	}
	
}
