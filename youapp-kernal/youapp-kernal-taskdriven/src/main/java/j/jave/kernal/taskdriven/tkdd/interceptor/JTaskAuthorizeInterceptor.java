package j.jave.kernal.taskdriven.tkdd.interceptor;

import j.jave.kernal.taskdriven.tkdd.JTask;
import j.jave.kernal.taskdriven.tkdd.JTaskExecutionException;
import j.jave.kernal.taskdriven.tkdd.JTaskInterceptor;
import j.jave.kernal.taskdriven.tkdd.JTaskInvocation;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadata;


public class JTaskAuthorizeInterceptor implements JTaskInterceptor<JTask> {
	
	@Override
	public Object interceptor(JTaskInvocation taskInvocation) {
		JTask task=taskInvocation.getTask();
		JTaskMetadata metadata= task.getRunningTaskMetadata();
		
		if(!metadata.authorize(task.getTaskContext().getSubject(task.getClass()))){
			throw new JTaskExecutionException("has unsufficient right to access");
		}
		return taskInvocation.proceed();
	}

}
