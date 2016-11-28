package j.jave.kernal.streaming.netty.client;

import java.lang.reflect.Method;

/**
 * combined with {@link IntarfaceImpl#asyncProxy()} to support ASYNC RPC.
 * @author JIAZJ
 *
 * @see ControllerCallPromise
 */
public interface ControllerAsyncCall {

	/**
	 * 
	 * @param proxy
	 * @param method
	 * @param args
	 * @param returnVal  the response result of RPC
	 */
	void success(Object proxy, Method method, Object[] args, Object returnVal);
	
	/**
	 * 
	 * @param proxy
	 * @param method
	 * @param args
	 * @param throwable
	 */
	void fail(Object proxy, Method method, Object[] args, Throwable throwable);
	
}
