package j.jave.kernal.streaming.netty.msg;

public interface RPCMsgDecoder<T> {

	/**
	 * 
	 * @param context
	 * @return
	 */
	Object[] decode(T context);
}
