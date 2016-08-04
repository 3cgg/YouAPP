package j.jave.kernal.jave.async;

public interface JAsyncExecutor<T> {

	Object execute(T data) throws Exception;
	
}
