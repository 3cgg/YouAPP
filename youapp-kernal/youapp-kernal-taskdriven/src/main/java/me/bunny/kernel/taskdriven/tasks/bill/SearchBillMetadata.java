package me.bunny.kernel.taskdriven.tasks.bill;

import me.bunny.kernel.taskdriven.tkdd.JDefaultTaskMetadata;
import me.bunny.kernel.taskdriven.tkdd.JTask;

public class SearchBillMetadata  extends JDefaultTaskMetadata{

	{
		setDescriber("Search Bill");
		setName(SearchBillMetadata.class.getName());
	}
	
	@Override
	public Class<? extends JTask> task() {
		return SearchBillTask.class;
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}

	@Override
	public void setDescriber(String describer) {
		this.describer=describer;
	}
	
}
