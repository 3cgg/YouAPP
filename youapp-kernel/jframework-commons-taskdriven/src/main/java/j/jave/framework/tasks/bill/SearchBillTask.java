package j.jave.framework.tasks.bill;

import j.jave.framework.commons.logging.JLoggerFactory;
import j.jave.framework.tkdd.JBaseTask;
import j.jave.framework.tkdd.JTaskContext;
import j.jave.framework.tkdd.JTaskMetadataHierarchy;
import j.jave.framework.tkdd.JTaskMetadataOnTask;


@JTaskMetadataHierarchy
@JTaskMetadataOnTask(value=SearchBillMetadata.class)
public class SearchBillTask extends JBaseTask {
	
	public SearchBillTask(JTaskContext taskContext) {
		super(taskContext);
	}

	@Override
	public Object run() {
		JLoggerFactory.getLogger(this.getClass()).info(this.getClass().getName());
		return null;
	}


}
