package me.bunny.modular._p.streaming.coordinator.command;

import me.bunny.modular._p.streaming.coordinator.CommandResource;

public class WorkflowErrorCommand extends WorkflowCommand<WorkflowErrorModel> {

	@Override
	protected void doExecute(WorkflowErrorModel commandModel, CommandResource commandResource) throws Exception {
		System.out.println(" instance("+commandModel.getIsntanceId()
		+") of workflow("+commandModel.getWorkflowName()+") is error,"
				+ " error message is "+commandModel.getMessage());

	}

}
