package j.jave.kernal.taskdriven.tkdd;

public interface JTaskInterceptor<T extends JTask> {

	Object interceptor(JTaskInvocation taskInvocation);
	
}
