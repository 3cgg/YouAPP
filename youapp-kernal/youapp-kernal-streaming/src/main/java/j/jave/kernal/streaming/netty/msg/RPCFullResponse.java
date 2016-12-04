package j.jave.kernal.streaming.netty.msg;

public interface RPCFullResponse {

	RPCMsgEncoder encoder();
	
	RPCFullResponse offer(Object object);
	
	/**
	 * get the data encoded by {@link #encoder()}
	 * @return
	 */
	Object get();
}
