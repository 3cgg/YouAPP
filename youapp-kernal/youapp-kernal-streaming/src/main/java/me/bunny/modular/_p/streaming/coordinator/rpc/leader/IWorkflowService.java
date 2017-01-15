package me.bunny.modular._p.streaming.coordinator.rpc.leader;

import java.util.Map;

import me.bunny.modular._p.streaming.coordinator.WorkflowMeta;
import me.bunny.modular._p.streaming.netty.controller.ControllerService;
import me.bunny.modular._p.streaming.netty.controller.IControllerImplementer;

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
