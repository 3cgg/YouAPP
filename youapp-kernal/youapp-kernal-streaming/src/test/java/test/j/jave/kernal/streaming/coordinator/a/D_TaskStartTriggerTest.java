package test.j.jave.kernal.streaming.coordinator.a;

import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.modular._p.streaming.coordinator.CoordinatorPaths;
import me.bunny.modular._p.streaming.coordinator.WorkflowMeta;
import me.bunny.modular._p.streaming.zookeeper.ZooKeeperConfig;
import me.bunny.modular._p.streaming.zookeeper.ZooKeeperConnector;
import me.bunny.modular._p.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

@Deprecated
public class D_TaskStartTriggerTest {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		
		ZooKeeperConfig zooKeeperConfig=new ZooKeeperConfig();
		zooKeeperConfig.setConnectString("nim1.storm.com:2182,nim2.storm.com");
		zooKeeperConfig.setNamespace("test-b");
		ZookeeperExecutor executor=new ZooKeeperConnector(zooKeeperConfig)
				.connect();
		
		WorkflowMeta demo=WorkflowMetaDemoTest.get();
		//basePath+"/workflow-trigger/"+workflow.getName();
		
		String path=CoordinatorPaths.BASE_PATH
				+"/workers-collection-repo/"+demo.getName();
		if(executor.exists(path)){
			executor.setPath(path, JJSON.get().formatObject(demo));
		}else{
		executor.createPath(path
				,JStringUtils.utf8(JJSON.get().formatObject(demo)));
		}
		Thread.currentThread().sleep(2000);
		
		executor.setPath(CoordinatorPaths.BASE_PATH
				+"/workflow-trigger",JJSON.get().formatObject(demo));
		Thread.currentThread().sleep(2000);
		
	}
	
}
