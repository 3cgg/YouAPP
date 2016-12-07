package test.j.jave.kernal.streaming.coordinator.b;

import java.util.Map;

import com.google.common.collect.Maps;

import j.jave.kernal.streaming.coordinator.WorkflowMeta;
import j.jave.kernal.streaming.coordinator.rpc.leader.IWorkflowService;
import j.jave.kernal.streaming.netty.client.SimpleInterfaceImplUtil;

public class TaskStartTrigger {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		
		WorkflowMeta demo=WorkflowMetaDemoTest.get();
		
		IWorkflowService workflowService=SimpleInterfaceImplUtil.syncProxy(IWorkflowService.class);
		
		workflowService.addWorkflow(demo);
		Thread.currentThread().sleep(2000);
		for(int i=0;i<15000;i++){
			Map<String, Object> pams=Maps.newHashMap();
			pams.put("val", i+"-nly");
			pams.put("index", i);
			workflowService.triggerWorkflow(demo.getName(), pams);
		}
		
		System.out.println("end...");
		Thread.currentThread().sleep(2000);
		System.exit(0);
		
	}
	
}
