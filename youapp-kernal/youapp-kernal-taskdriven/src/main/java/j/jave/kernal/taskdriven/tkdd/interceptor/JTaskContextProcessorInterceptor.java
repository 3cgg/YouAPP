package j.jave.kernal.taskdriven.tkdd.interceptor;

import j.jave.kernal.taskdriven.tkdd.JTask;
import j.jave.kernal.taskdriven.tkdd.JTaskContext;
import j.jave.kernal.taskdriven.tkdd.JTaskExecutionException;
import j.jave.kernal.taskdriven.tkdd.JTaskInterceptor;
import j.jave.kernal.taskdriven.tkdd.JTaskInvocation;


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
