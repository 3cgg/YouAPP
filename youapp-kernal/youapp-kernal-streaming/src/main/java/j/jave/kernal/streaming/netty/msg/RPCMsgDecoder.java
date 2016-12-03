package j.jave.kernal.streaming.netty.msg;

import j.jave.kernal.streaming.netty.controller.MappingMeta;

public interface RPCMsgDecoder {

	Object[] decode(MappingMeta mappingMeta);
}
