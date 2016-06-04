package j.jave.kernal.dataexchange.impl;

import j.jave.kernal.dataexchange.model.MessageMeta;
import j.jave.kernal.jave.model.JModel;

public interface JMessageMetaDecoder<T extends JModel> {
	
	T encode(MessageMeta messageMeta) throws Exception;

}
