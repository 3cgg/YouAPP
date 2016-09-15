package j.jave.kernal.taskdriven.tkdd;

import j.jave.kernal.taskdriven.tkdd.flow.JFlowContext;

/**
 * basic task implementation for simply any new additional task.
 * @author J
 *
 */
public abstract class JBaseTask implements JTask ,JTaskExecutePostProcessor{
	
	private Class<? extends JBaseTask> clazz=this.getClass();
	
	protected JTaskContext taskContext;
	
	/**
	 * current meta data tied to the task.
	 */
	protected JTaskMetadata taskMetadata;
	
	protected JTaskScope taskScope;
	
	public JBaseTask(JTaskContext taskContext) {
		this.taskContext=taskContext;
	}
	
	public JBaseTask() {
		this(new JTaskContext());
	}
	
	@Override
	public final Object start() {
		setTaskScope(JTaskScope.BEFORE_EXECUTE);
		postProcessBeforeExecute();
		setTaskScope(JTaskScope.EXECUTING);
		Object object= JTaskExecutors.newSingleTaskExecutor().execute(this);
		setTaskScope(JTaskScope.AFTER_EXECUTE);
		postProcessAfterExecute();
		setTaskScope(JTaskScope.COMPELTED);
		return object;
	}
	
	@Override
	public final Object start(JFlowContext flowContext) {
		JTaskContext taskContext=new JTaskContext(flowContext);
		this.taskContext=taskContext;
		return start();
	}
	
	/**
	 * default implementation , if no more interceptors need.
	 */
	public final JTaskInvocation getTaskInvocation(){
		return new JDefaultTaskInvocation(this, taskContext);
	}
	
	
	@Override
	public JTaskMetadata getCustomTaskMetadata() {
		try {
			//get from custom.
			JTaskMetadataSpecInit taskMetadataSpecInit=  taskContext.getFlowContext().getCustomTaskMetadataSpec(clazz);
			if(taskMetadataSpecInit!=null){
				taskMetadata=taskMetadataSpecInit.custom();
				return taskMetadata;
			}
			return null;
		} catch (Exception e) {
			throw new JTaskExecutionException(e);
		}
	}
	
	
	@Override
	public JTaskMetadata getDefineTaskMetadata() {
		try {
			// get default definition.
			JTaskMetadataOnTask metadataOnTask= this.getClass().getAnnotation(JTaskMetadataOnTask.class);
			taskMetadata= metadataOnTask.value().newInstance();
			return taskMetadata;
		} catch (InstantiationException e) {
			throw new JTaskExecutionException(e);
		} catch (IllegalAccessException e) {
			throw new JTaskExecutionException(e);
		}
	}
	
	@Override
	public JTaskMetadata getSystemDynamicSnapshotTaskMetadata() {
		
		JTaskMetadata taskMetadata=JRepo.get().getDynamicSnapshotMetadata(this, taskContext);
		// get default definition.
		if (taskMetadata == null) {
			taskMetadata = getDefineTaskMetadata();
		}
		return taskMetadata;
	}
	
	@Override
	public JTaskMetadata getCachedTaskMetadata() {
		return taskMetadata;
	}
	
	@Override
	public JTaskMetadata getRunningTaskMetadata() {

		//get from cache
		JTaskMetadata taskMetadata=getCachedTaskMetadata();

		//get from custom.
		if(taskMetadata==null){
			taskMetadata=getCustomTaskMetadata();
		}
		
		// get from dynamical metadata from repository.
		if(taskMetadata==null){
			taskMetadata=getSystemDynamicSnapshotTaskMetadata();
		}
		
		// get default definition.
		if(taskMetadata==null){
			taskMetadata=getDefineTaskMetadata();
		}
		return taskMetadata;
	}
	
	public final JTaskContext getTaskContext() {
		return taskContext;
	}
	
	public final JFlowContext getFlowContext(){
		return getTaskContext().getFlowContext();
	}
	
	
	/**
	 * one use to override the method to provide the real functionality.
	 */
	@Override
	public void postProcessBeforeExecute() throws JTaskExecutionException {
	}
	
	/**
	 * one use to override the method to provide the real functionality.
	 */
	@Override
	public void postProcessAfterExecute() throws JTaskExecutionException {
	}
	
	@Override
	public JTaskScope getScope() {
		return this.taskScope;
	}

	void setTaskScope(JTaskScope taskScope) {
		this.taskScope = taskScope;
	}
	
}
