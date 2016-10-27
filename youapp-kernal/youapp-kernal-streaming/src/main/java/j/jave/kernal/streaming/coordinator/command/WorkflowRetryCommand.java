package j.jave.kernal.streaming.coordinator.command;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.streaming.coordinator.CommandResource;
import j.jave.kernal.streaming.coordinator.CoordinatorPaths;

public class WorkflowRetryCommand  extends WorkflowCommand<WorkflowRetryModel> {

	@Override
	protected void doExecute(WorkflowRetryModel commandModel,CommandResource commandResource) throws Exception {
		
		if(commandModel.getCount()<commandModel.getMaxCount()){
			System.out.println(" instance("+commandModel.getIsntanceId()
			+"),count("+commandModel.getCount()+"); from workflow("+commandModel.getWorkflowName()+") is completed,"
					+ " we need restart the worklow to the max count : "+commandModel.getMaxCount());

			commandResource.getExecutor().
			setPath(CoordinatorPaths.BASE_PATH
					+"/workflow-trigger",
					JJSON.get().formatObject(commandModel.getWorkflow().getWorkflowMeta()));
			commandModel.setCount(commandModel.getCount()+1);
		}
		else{
			System.out.println(" instance("+commandModel.getIsntanceId()
			+"),count("+commandModel.getCount()+"); from workflow("+commandModel.getWorkflowName()+") is completed,"
					+ " we need end the worklow due to the max count : "+commandModel.getMaxCount());

		}
	}
	
}
