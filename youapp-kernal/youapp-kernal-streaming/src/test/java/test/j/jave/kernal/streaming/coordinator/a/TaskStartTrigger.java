package test.j.jave.kernal.streaming.coordinator.a;

import java.util.Map;

import com.google.common.collect.Maps;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.streaming.coordinator.CoordinatorPaths;
import j.jave.kernal.streaming.coordinator.WorkflowMeta;
import j.jave.kernal.streaming.coordinator.rpc.leader.IWorkflowService;
import j.jave.kernal.streaming.netty.client.SimpleInterfaceImplUtil;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import j.jave.kernal.streaming.zookeeper.ZooKeeperExecutorGetter;

public class TaskStartTrigger {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		
		ZookeeperExecutor executor=ZooKeeperExecutorGetter.getDefault();
		
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
		
		IWorkflowService workflowService=SimpleInterfaceImplUtil.syncProxy(IWorkflowService.class);
		Map<String, Object> pams=Maps.newHashMap();
		pams.put("vl", "0nly");
		workflowService.triggerWorkflow(demo.getName(), pams);
		Thread.currentThread().sleep(2000);
		
	}
	
}
