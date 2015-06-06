package j.jave.framework.tasks.bill;

import j.jave.framework.tkdd.JBaseTask;
import j.jave.framework.tkdd.JTaskContext;
import j.jave.framework.tkdd.JTaskMetadataHierarchy;
import j.jave.framework.tkdd.JTaskMetadataOnTask;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask(value=RecordBillMetadata.class)
public class RecordBillTask extends JBaseTask{

	public RecordBillTask(JTaskContext taskContext) {
		super(taskContext);
	}

	@Override
	public Object run() {
		System.out.println(this.getClass().getName()+taskContext.getRoot());
		return null;
	}

}
