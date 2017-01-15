package me.bunny.kernel.taskdriven.tkdd;

public interface JTaskInterceptor<T extends JTask> {

	Object interceptor(JTaskInvocation taskInvocation);
	
}
