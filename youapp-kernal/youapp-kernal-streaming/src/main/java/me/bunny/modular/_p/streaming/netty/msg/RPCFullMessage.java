package me.bunny.modular._p.streaming.netty.msg;

import me.bunny.modular._p.streaming.netty.controller.MappingMeta;

public interface RPCFullMessage extends FullMessage {

	RPCMsgDecoder<MappingMeta> decoder();

	RPCFullResponseWriter response();
	
}
