package j.jave.kernal.streaming.coordinator.command;

import j.jave.kernal.streaming.coordinator.CommandResource;

public class WorkflowCompleteCommand  extends WorkflowCommand<WorkflowCompleteModel> {

	@Override
	protected void doExecute(WorkflowCompleteModel commandModel,CommandResource commandResource) throws Exception {
		
		System.out.println(" instance("+commandModel.getIsntanceId()
									+") from workflow("+commandModel.getWorkflowName()+") is completed,"
											+ " status is "+commandModel.getStatus());
		
	}
	
}
