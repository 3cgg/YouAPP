package me.bunny.modular._p.taskdriven.tkdd;

public interface JTaskInterceptor<T extends JTask> {

	Object interceptor(JTaskInvocation taskInvocation);
	
}
