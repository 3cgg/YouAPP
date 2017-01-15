package me.bunny.kernel.dataexchange.impl;

import me.bunny.kernel.dataexchange.model.MessageMeta;
import me.bunny.kernel.jave.model.JModel;

public interface JMessageMetaDecoder<T extends JModel> {
	
	T encode(MessageMeta messageMeta) throws Exception;

}
