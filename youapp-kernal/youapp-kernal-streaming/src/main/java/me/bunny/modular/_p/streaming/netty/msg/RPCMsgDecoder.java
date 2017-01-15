package me.bunny.modular._p.streaming.netty.msg;

public interface RPCMsgDecoder<T> {

	/**
	 * 
	 * @param context
	 * @return
	 */
	Object[] decode(T context);
}
