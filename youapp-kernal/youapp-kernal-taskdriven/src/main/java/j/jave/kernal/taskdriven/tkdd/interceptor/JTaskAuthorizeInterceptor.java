package j.jave.kernal.taskdriven.tkdd.interceptor;

import j.jave.kernal.taskdriven.tkdd.JBaseTask;
import j.jave.kernal.taskdriven.tkdd.JTask;
import j.jave.kernal.taskdriven.tkdd.JTaskContext;
import j.jave.kernal.taskdriven.tkdd.JTaskExecutionException;
import j.jave.kernal.taskdriven.tkdd.JTaskInterceptor;
import j.jave.kernal.taskdriven.tkdd.JTaskInvocation;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadata;


public class JTaskAuthorizeInterceptor<T extends JTask> implements JTaskInterceptor<T> {

	/**
	 * the entity that maps to a concrete one.
	 * {@link JTable} 
	 */
	private final T object;
	
	private JTaskContext taskContext;
	
	public JTaskAuthorizeInterceptor(T object,JTaskContext taskContext) {
		this.object=object;
		this.taskContext=taskContext;
	}
	
	@Override
	public T interceptor(JTaskInvocation<T> taskInvocation) {
		JTaskMetadata metadata= object.getRunningTaskMetadata();
		
		if(!metadata.authorize(((JBaseTask)object).getTaskContext().getSubject(object.getClass()))){
			throw new JTaskExecutionException("has unsufficient right to access");
		}
		return taskInvocation.proceed();
	}

}
