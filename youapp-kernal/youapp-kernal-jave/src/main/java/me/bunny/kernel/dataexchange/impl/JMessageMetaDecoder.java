package me.bunny.kernel.dataexchange.impl;

import me.bunny.kernel._c.model.JModel;
import me.bunny.kernel.dataexchange.model.MessageMeta;

public interface JMessageMetaDecoder<T extends JModel> {
	
	T encode(MessageMeta messageMeta) throws Exception;

}
