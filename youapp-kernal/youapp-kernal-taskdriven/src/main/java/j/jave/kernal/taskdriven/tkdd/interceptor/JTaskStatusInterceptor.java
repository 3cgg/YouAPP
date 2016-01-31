package j.jave.kernal.taskdriven.tkdd.interceptor;

import j.jave.kernal.taskdriven.tkdd.JRepo;
import j.jave.kernal.taskdriven.tkdd.JTask;
import j.jave.kernal.taskdriven.tkdd.JTaskContext;
import j.jave.kernal.taskdriven.tkdd.JTaskExecutionException;
import j.jave.kernal.taskdriven.tkdd.JTaskInterceptor;
import j.jave.kernal.taskdriven.tkdd.JTaskInvocation;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadata;


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
