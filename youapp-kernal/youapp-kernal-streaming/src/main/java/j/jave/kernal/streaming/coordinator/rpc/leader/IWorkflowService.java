package j.jave.kernal.streaming.coordinator.rpc.leader;

import java.util.Map;

import j.jave.kernal.streaming.coordinator.WorkflowMeta;
import j.jave.kernal.streaming.netty.controller.ControllerService;
import j.jave.kernal.streaming.netty.controller.IControllerImplementer;

public interface IWorkflowService extends ControllerService , IControllerImplementer<WorkflowService>{

	/**
	 * 
	 * @param workflowMeta
	 * @return
	 */
	boolean addWorkflow(WorkflowMeta workflowMeta);
	
	/**
	 * 
	 * @param name  workflow name
	 * @return
	 */
	boolean removeWorkflow(String name);
	
	/**
	 * 
	 * @param name workflow name
	 * @param conf  some parameters/configs used in the workflow
	 * @return
	 */
	boolean triggerWorkflow(String name,Map<String, Object> conf);
	
	boolean sendHeartbeats(ExecutingWorker executingWorker);
	
}
