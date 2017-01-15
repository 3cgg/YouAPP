package me.bunny.modular._p.taskdriven.tkdd.interceptor;

import me.bunny.modular._p.taskdriven.tkdd.JTask;
import me.bunny.modular._p.taskdriven.tkdd.JTaskInterceptor;
import me.bunny.modular._p.taskdriven.tkdd.JTaskInvocation;


public class JTaskExecutingInterceptor implements JTaskInterceptor<JTask> {

	@Override
	public Object interceptor(JTaskInvocation taskInvocation) {
		return taskInvocation.getTask().run();
	}

}
