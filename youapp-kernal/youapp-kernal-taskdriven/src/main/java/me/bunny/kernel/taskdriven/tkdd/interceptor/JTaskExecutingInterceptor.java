package me.bunny.kernel.taskdriven.tkdd.interceptor;

import me.bunny.kernel.taskdriven.tkdd.JTask;
import me.bunny.kernel.taskdriven.tkdd.JTaskInterceptor;
import me.bunny.kernel.taskdriven.tkdd.JTaskInvocation;


public class JTaskExecutingInterceptor implements JTaskInterceptor<JTask> {

	@Override
	public Object interceptor(JTaskInvocation taskInvocation) {
		return taskInvocation.getTask().run();
	}

}
