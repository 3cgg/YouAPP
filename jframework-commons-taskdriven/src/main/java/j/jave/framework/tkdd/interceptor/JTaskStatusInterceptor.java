package j.jave.framework.tkdd.interceptor;

import j.jave.framework.tkdd.JRepo;
import j.jave.framework.tkdd.JTask;
import j.jave.framework.tkdd.JTaskContext;
import j.jave.framework.tkdd.JTaskExecutionException;
import j.jave.framework.tkdd.JTaskInterceptor;
import j.jave.framework.tkdd.JTaskInvocation;
import j.jave.framework.tkdd.JTaskMetadata;


public class JTaskStatusInterceptor<T extends JTask> implements JTaskInterceptor<T> {

	/**
	 * the entity that maps to a concrete one.
	 * {@link JTable} 
	 */
	private final T object;
	
	private JTaskContext taskContext;
	
	public JTaskStatusInterceptor(T object,JTaskContext taskContext) {
		this.object=object;
		this.taskContext=taskContext;
	}
	
	@Override
	public T interceptor(JTaskInvocation<T> taskInvocation) {
		JTaskMetadata metadata= object.getRunningTaskMetadata();
		
		if(metadata.uninstalled()){
			throw new JTaskExecutionException("the task is uninstalled.");
		}
		
		if(!metadata.enabled()){
			throw new JTaskExecutionException("the task is disable.");
		}
		return taskInvocation.proceed();
	}

}
