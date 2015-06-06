package j.jave.framework.tkdd;

/**
 * @author J
 */
public interface JTask {

	/**
	 * notify task framework to ready to process this task.
	 * @return
	 */
	Object start();
	
	/**
	 * actual something that is processed.
	 * @return
	 */
	Object run();
	
	/**
	 * get running metadata related to the task.
	 * <p>first check if the local has the metadata
	 * then if the former is null,  call {@link #getCustomTaskMetadata()} , 
	 * then if the former is null ,call {@link #getDefineTaskMetadata()}
	 * @return
	 */
	JTaskMetadata getRunningTaskMetadata();
	
	/**
	 * get custom metadata related to the task.
	 * @return
	 */
	JTaskMetadata getCustomTaskMetadata();
	
	
	/**
	 * get snapshot metadata related to the task.
	 * <p>first call {@link #getCustomTaskMetadata()} , 
	 * then if the former is null ,call {@link #getDefineTaskMetadata()}
	 * @return
	 */
	JTaskMetadata getSnapshotTaskMetadata();
	
	/**
	 * get defined metadata related to the task.
	 * @return
	 */
	JTaskMetadata getDefineTaskMetadata();
	
	/**
	 * get the related invocation.
	 * @return
	 */
	<T extends JTask> JTaskInvocation<T> getTaskInvocation();
	
}
