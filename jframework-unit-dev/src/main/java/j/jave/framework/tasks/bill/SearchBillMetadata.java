package j.jave.framework.tasks.bill;

import j.jave.framework.tkdd.JDefaultTaskMetadata;
import j.jave.framework.tkdd.JTask;

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
