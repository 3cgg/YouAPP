package j.jave.framework.tkdd;


public abstract class JBaseTask implements JTask {
	
	private Class<? extends JBaseTask> clazz=this.getClass();
	
	protected final JTaskContext taskContext;
	
	protected JTaskMetadata taskMetadata;
	
	public JBaseTask(JTaskContext taskContext) {
		this.taskContext=taskContext;
	}
	
	@Override
	public Object start() {
		return JTaskExecutors.newSingleTaskExecutor().execute(this);
	}
	
	/**
	 * default implementation , if no more interceptors need.
	 */
	public <T extends JTask> JTaskInvocation<T> getTaskInvocation(){
		return new JDefaultTaskInvocation<T>((T)this, taskContext);
	}
	
	
	@Override
	public JTaskMetadata getCustomTaskMetadata() {
		try {
			//get from custom.
			Class<? extends JTaskMetadataSpecInit> taskMetadataSpecInitClazz=  taskContext.getCustomTaskMetadataSpec(clazz);
			if(taskMetadataSpecInitClazz!=null){
				JTaskMetadataSpecInit taskMetadataSpecInit= taskMetadataSpecInitClazz.newInstance();
				taskMetadata=taskMetadataSpecInit.custom();
				return taskMetadata;
			}
			return null;
		} catch (InstantiationException e) {
			throw new JTaskExecutionException(e);
		} catch (IllegalAccessException e) {
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
	public JTaskMetadata getSnapshotTaskMetadata() {
		
		JTaskMetadata taskMetadata=getCustomTaskMetadata();
		// get default definition.
		if (taskMetadata == null) {
			taskMetadata = getDefineTaskMetadata();
		}
		return taskMetadata;
	}
	
	@Override
	public JTaskMetadata getRunningTaskMetadata() {

		//get from local
		if(taskMetadata!=null) {
			return taskMetadata;
		}

		//get from custom.
		if(taskMetadata==null){
			taskMetadata=getCustomTaskMetadata();
		}
		
		// get default definition.
		if(taskMetadata==null){
			taskMetadata=getDefineTaskMetadata();
		}
		return taskMetadata;
	}
	
}
