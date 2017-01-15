package me.bunny.modular._p.taskdriven.tkdd.interceptor;

import me.bunny.modular._p.taskdriven.tkdd.JTask;
import me.bunny.modular._p.taskdriven.tkdd.JTaskExecutionException;
import me.bunny.modular._p.taskdriven.tkdd.JTaskInterceptor;
import me.bunny.modular._p.taskdriven.tkdd.JTaskInvocation;
import me.bunny.modular._p.taskdriven.tkdd.JTaskMetadata;


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
