package j.jave.framework.tkdd;

public interface JTaskInterceptor<T extends JTask> {

	T interceptor(JTaskInvocation<T> taskInvocation);
	
}
