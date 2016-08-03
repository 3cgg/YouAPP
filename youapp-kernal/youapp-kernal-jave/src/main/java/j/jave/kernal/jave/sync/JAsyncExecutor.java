package j.jave.kernal.jave.sync;

public interface JAsyncExecutor<T> {

	Object execute(T data) throws Exception;
	
}
