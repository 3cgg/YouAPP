package j.jave.framework.tasks.bill;

import j.jave.framework.tkdd.JBaseTask;
import j.jave.framework.tkdd.JTaskContext;
import j.jave.framework.tkdd.JTaskMetadataHierarchy;
import j.jave.framework.tkdd.JTaskMetadataHierarchy.Hierarchy;
import j.jave.framework.tkdd.JTaskMetadataOnTask;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask(value=ActivitiDemoMetadata.class)
public class ActivitiDemoTask extends JBaseTask {
	
	@JTaskMetadataHierarchy(hierarchy=Hierarchy.CHILD)
	private RecordBillTask recordBillTask=new RecordBillTask(taskContext);
	
	@JTaskMetadataHierarchy(hierarchy=Hierarchy.CHILD)
	private SearchBillTask searchBillTask=new SearchBillTask(taskContext);

	public ActivitiDemoTask(JTaskContext taskContext) {
		super(taskContext);
	}

	@Override
	public Object run() {
		
		recordBillTask.start();
		
		searchBillTask.start();
		
		return null;
	}

}
