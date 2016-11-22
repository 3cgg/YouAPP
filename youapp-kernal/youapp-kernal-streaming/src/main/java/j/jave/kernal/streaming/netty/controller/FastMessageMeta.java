package j.jave.kernal.streaming.netty.controller;

import j.jave.kernal.dataexchange.model.MessageMeta;

public interface FastMessageMeta extends MessageMeta {

	byte[] bytes();
	
}
