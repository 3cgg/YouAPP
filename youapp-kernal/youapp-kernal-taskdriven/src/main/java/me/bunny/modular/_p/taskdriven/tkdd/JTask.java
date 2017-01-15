package me.bunny.modular._p.taskdriven.tkdd;

import me.bunny.modular._p.taskdriven.tkdd.flow.JFlowContext;

/**
 * @author J
 */
public interface JTask extends JNotifyStart {
	
	/**
	 * actual something that is processed. delegate the super start method.
	 * @return
	 */
	Object run() throws JTaskExecutionException;
	
	/**
	 * get running metadata related to the task.
	 * <p>first check if the local cache has the metadata
	 * then if the former is null,  call {@link #getCustomTaskMetadata()} , 
	 * then if the former is null,  call {@link #getSystemDynamicSnapshotTaskMetadata()}
	 * then if the former is null ,call {@link #getDefineTaskMetadata()}
	 * @return
	 */
	JTaskMetadata getRunningTaskMetadata();
	
	/**
	 * get cached task meta data, which is for active task with exisitng task metadata.
	 * @return
	 */
	JTaskMetadata getCachedTaskMetadata();
	
	/**
	 * get custom metadata related to the task.
	 * @return
	 */
	JTaskMetadata getCustomTaskMetadata();
	
	/**
	 * get task metadata from repository, some may be dynamically changed by the system control service any time.
	 * @return
	 */
	JTaskMetadata getSystemDynamicSnapshotTaskMetadata();
	
	/**
	 * get defined metadata related to the task.
	 * @return
	 */
	JTaskMetadata getDefineTaskMetadata();
	
	/**
	 * get the related invocation.
	 * @return
	 */
	JTaskInvocation getTaskInvocation();
	
	/**
	 * indicate the current scope, i.e. the task is in the initialization , executing or others.
	 * @return
	 */
	JTaskScope getScope();
	
	JTaskContext getTaskContext(); 
	
	JFlowContext getFlowContext();
}
