package me.bunny.kernel.jave.async;

public interface JAsyncExecutor<T> {

	Object execute(T data) throws Exception;
	
}
