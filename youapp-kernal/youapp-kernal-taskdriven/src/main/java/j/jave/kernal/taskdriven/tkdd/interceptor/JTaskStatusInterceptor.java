package j.jave.kernal.taskdriven.tkdd.interceptor;

import j.jave.kernal.taskdriven.tkdd.JTask;
import j.jave.kernal.taskdriven.tkdd.JTaskExecutionException;
import j.jave.kernal.taskdriven.tkdd.JTaskInterceptor;
import j.jave.kernal.taskdriven.tkdd.JTaskInvocation;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadata;


public class JTaskStatusInterceptor implements JTaskInterceptor<JTask> {

	@Override
	public Object interceptor(JTaskInvocation taskInvocation) {
		JTask task=taskInvocation.getTask();
		JTaskMetadata metadata= task.getRunningTaskMetadata();
		
		if(metadata.uninstalled()){
			throw new JTaskExecutionException("the task is uninstalled.");
		}
		
		if(!metadata.enabled()){
			throw new JTaskExecutionException("the task is disable.");
		}
		return taskInvocation.proceed();
	}

}
