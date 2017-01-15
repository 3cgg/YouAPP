package j.jave.kernal.taskdriven.tasks.bill;

import j.jave.kernal.taskdriven.tkdd.JBaseTask;
import j.jave.kernal.taskdriven.tkdd.JTaskContext;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;
import me.bunny.kernel.jave.logging.JLoggerFactory;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask(value=ActivitiDemoMetadata.class)
public class ActivitiDemoTask extends JBaseTask {
	
//	@JTaskMetadataHierarchy(hierarchy=Hierarchy.CHILD)
//	private RecordBillTask recordBillTask=new RecordBillTask(taskContext);
//	
//	@JTaskMetadataHierarchy(hierarchy=Hierarchy.CHILD)
//	private SearchBillTask searchBillTask=new SearchBillTask(taskContext);

	public ActivitiDemoTask(JTaskContext taskContext) {
		super(taskContext);
	}

	@Override
	public Object run() {
		JLoggerFactory.getLogger(this.getClass()).info(this.getClass().getName());
		return null;
	}

}
