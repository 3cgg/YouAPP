package me.bunny.kernel._c.async;

public interface JAsyncExecutor<T> {

	Object execute(T data) throws Exception;
	
}
