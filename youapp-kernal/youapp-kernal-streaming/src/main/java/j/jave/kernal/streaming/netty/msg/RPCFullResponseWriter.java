package j.jave.kernal.streaming.netty.msg;

public interface RPCFullResponseWriter {

	RPCMsgEncoder encoder();
	
	RPCFullResponseWriter offer(Object object);
	
	/**
	 * get the data encoded by {@link #encoder()}
	 * @return
	 */
	Object get();
}
