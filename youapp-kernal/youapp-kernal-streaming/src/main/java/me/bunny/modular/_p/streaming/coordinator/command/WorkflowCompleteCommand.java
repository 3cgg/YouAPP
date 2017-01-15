package me.bunny.modular._p.streaming.coordinator.command;

import me.bunny.modular._p.streaming.coordinator.CommandResource;

public class WorkflowCompleteCommand  extends WorkflowCommand<WorkflowCompleteModel> {

	@Override
	protected void doExecute(WorkflowCompleteModel commandModel,CommandResource commandResource) throws Exception {
		
		System.out.println(" instance("+commandModel.getIsntanceId()
									+") from workflow("+commandModel.getWorkflowName()+") is completed,"
											+ " status is "+commandModel.getStatus());
		
	}
	
}
