package j.jave.framework.tkdd.interceptor;

import j.jave.framework.tkdd.JTask;
import j.jave.framework.tkdd.JTaskContext;
import j.jave.framework.tkdd.JTaskExecutionException;
import j.jave.framework.tkdd.JTaskInterceptor;
import j.jave.framework.tkdd.JTaskInvocation;


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
		taskContext.getFlowContext().setCurrent(object.getClass());
		taskContext.getFlowContext().setCurrentTaskIndex(taskContext.getFlowContext().getCurrentTaskIndex()+1);
		if(taskContext.validate()){
			throw new JTaskExecutionException("task context is invalid.");
		}
		return taskInvocation.proceed();
	}

}
