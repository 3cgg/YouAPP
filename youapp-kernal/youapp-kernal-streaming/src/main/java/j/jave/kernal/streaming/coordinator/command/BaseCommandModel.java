package j.jave.kernal.streaming.coordinator.command;

import j.jave.kernal.streaming.coordinator.Workflow;
import j.jave.kernal.streaming.coordinator.command.WorkflowCommand.WorkflowCommandModel;

public class BaseCommandModel implements WorkflowCommandModel {
	
	private transient Workflow workflow;
	
	protected String workflowName;
	
	protected long isntanceId;
	
	protected long recordTime;

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public long getIsntanceId() {
		return isntanceId;
	}

	public void setIsntanceId(long isntanceId) {
		this.isntanceId = isntanceId;
	}

	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
	
}
