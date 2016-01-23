package j.jave.framework.tasks.bill;

import j.jave.framework.tkdd.JDefaultTaskMetadata;
import j.jave.framework.tkdd.JTask;

public class ActivitiDemoMetadata extends JDefaultTaskMetadata{

	{
		setDescriber("Activiti Demo Bill");
		setName(ActivitiDemoMetadata.class.getName());
	}
	
	@Override
	public void setName(String name) {
		this.name=name;	
	}

	@Override
	public void setDescriber(String describer) {
		this.describer=describer;
	}

	@Override
	public Class<? extends JTask> task() {
		return ActivitiDemoTask.class;
	}
	
}
