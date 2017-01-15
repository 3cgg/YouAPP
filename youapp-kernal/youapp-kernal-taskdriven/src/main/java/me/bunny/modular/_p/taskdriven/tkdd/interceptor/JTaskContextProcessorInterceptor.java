package me.bunny.modular._p.taskdriven.tkdd.interceptor;

import me.bunny.modular._p.taskdriven.tkdd.JTask;
import me.bunny.modular._p.taskdriven.tkdd.JTaskContext;
import me.bunny.modular._p.taskdriven.tkdd.JTaskExecutionException;
import me.bunny.modular._p.taskdriven.tkdd.JTaskInterceptor;
import me.bunny.modular._p.taskdriven.tkdd.JTaskInvocation;


public class JTaskContextProcessorInterceptor implements JTaskInterceptor<JTask> {

	@Override
	public Object interceptor(JTaskInvocation taskInvocation) {
		JTask task=taskInvocation.getTask();
		JTaskContext taskContext=task.getTaskContext();
		taskContext.getFlowContext().setCurrent(task.getClass());
		taskContext.getFlowContext().setCurrentTaskIndex(taskContext.getFlowContext().getCurrentTaskIndex()+1);
		if(taskContext.validate()){
			throw new JTaskExecutionException("task context is invalid.");
		}
		return taskInvocation.proceed();
	}

}
