package j.jave.framework.tkdd;

import j.jave.framework.model.support.JTable;

public class JTaskContextProcessorInterceptor<T extends JTask> implements JTaskInterceptor<T> {

	/**
	 * the entity that maps to a concrete one.
	 * {@link JTable} 
	 */
	private final T object;
	
	private JTaskContext taskContext;
	
	public JTaskContextProcessorInterceptor(T object,JTaskContext taskContext) {
		this.object=object;
		this.taskContext=taskContext;
	}
	
	@Override
	public T interceptor(JTaskInvocation<T> taskInvocation) {
		taskContext.setCurrent(object.getClass());
		taskContext.setCurrentTaskIndex(taskContext.getCurrentTaskIndex()+1);
		if(taskContext.validate()){
			throw new JTaskExecutionException("task context is invalid.");
		}
		return taskInvocation.proceed();
	}

}
