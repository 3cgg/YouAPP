package me.bunny.modular._p.taskdriven.tasks.bill;

import me.bunny.modular._p.taskdriven.tkdd.JDefaultTaskMetadata;
import me.bunny.modular._p.taskdriven.tkdd.JTask;

public class RecordBillMetadata extends JDefaultTaskMetadata{

	{
		setDescriber("Record Bill");
		setName(RecordBillMetadata.class.getName());
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
		return RecordBillTask.class;
	}
	
}
