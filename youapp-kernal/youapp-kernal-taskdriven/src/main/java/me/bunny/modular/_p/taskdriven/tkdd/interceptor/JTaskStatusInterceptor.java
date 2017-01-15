package me.bunny.modular._p.taskdriven.tkdd.interceptor;

import me.bunny.modular._p.taskdriven.tkdd.JTask;
import me.bunny.modular._p.taskdriven.tkdd.JTaskExecutionException;
import me.bunny.modular._p.taskdriven.tkdd.JTaskInterceptor;
import me.bunny.modular._p.taskdriven.tkdd.JTaskInvocation;
import me.bunny.modular._p.taskdriven.tkdd.JTaskMetadata;


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
