package me.bunny.kernel.taskdriven.tasks.bill;

import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel.taskdriven.tkdd.JBaseTask;
import me.bunny.kernel.taskdriven.tkdd.JTaskContext;
import me.bunny.kernel.taskdriven.tkdd.JTaskMetadataHierarchy;
import me.bunny.kernel.taskdriven.tkdd.JTaskMetadataOnTask;


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
