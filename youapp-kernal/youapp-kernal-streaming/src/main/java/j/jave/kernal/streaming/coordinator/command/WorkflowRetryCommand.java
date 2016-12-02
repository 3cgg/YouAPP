package j.jave.kernal.streaming.coordinator.command;

import j.jave.kernal.streaming.coordinator.CommandResource;
import j.jave.kernal.streaming.coordinator.NodeLeader;

public class WorkflowRetryCommand  extends WorkflowCommand<WorkflowRetryModel> {

	@Override
	protected void doExecute(WorkflowRetryModel commandModel,CommandResource commandResource) throws Exception {
		
		if(commandModel.getCount()<commandModel.getMaxCount()){
			System.out.println(" instance("+commandModel.getIsntanceId()
			+"),count("+commandModel.getCount()+"); from workflow("+commandModel.getWorkflowName()+") is completed,"
					+ " we need restart the worklow to the max count : "+commandModel.getMaxCount());
			
			NodeLeader.runtime().startWorkflow(commandModel.getWorkflowName()
					, commandModel.getConf());
			commandModel.setCount(commandModel.getCount()+1);
		}
		else{
			System.out.println(" instance("+commandModel.getIsntanceId()
			+"),count("+commandModel.getCount()+"); from workflow("+commandModel.getWorkflowName()+") is completed,"
					+ " we need end the worklow due to the max count : "+commandModel.getMaxCount());

		}
	}
	
}
