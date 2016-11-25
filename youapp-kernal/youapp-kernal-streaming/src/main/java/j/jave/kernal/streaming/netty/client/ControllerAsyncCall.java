package j.jave.kernal.streaming.netty.client;

/**
 * combined with {@link IntarfaceImpl#asyncProxy()} to support ASYNC RPC.
 * @author JIAZJ
 *
 * @see ControllerCallPromise
 */
public interface ControllerAsyncCall {

	/**
	 * 
	 * @param object  the response result of RPC
	 */
	void run(Object object);
	
}
