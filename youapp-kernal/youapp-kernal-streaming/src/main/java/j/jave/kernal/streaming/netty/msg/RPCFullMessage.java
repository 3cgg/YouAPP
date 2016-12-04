package j.jave.kernal.streaming.netty.msg;

import j.jave.kernal.streaming.netty.controller.MappingMeta;

public interface RPCFullMessage extends FullMessage {

	RPCMsgDecoder<MappingMeta> decoder();

	RPCFullResponseWriter response();
	
}
