package j.jave.kernal.taskdriven.tkdd;

public interface JTaskInterceptor<T extends JTask> {

	T interceptor(JTaskInvocation<T> taskInvocation);
	
}
