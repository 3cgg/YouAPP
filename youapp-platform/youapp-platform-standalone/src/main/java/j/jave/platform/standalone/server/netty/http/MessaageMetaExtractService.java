package j.jave.platform.standalone.server.netty.http;

import j.jave.kernal.dataexchange.modelprotocol.JByteDecoder;
import j.jave.kernal.dataexchange.modelprotocol.JMessageReceiverBuilder;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.support.parser.JParser;
import j.jave.platform.standalone.data.MessageMeta;
import j.jave.platform.standalone.interimpl.DefaultMessageMeta;

import java.net.URI;

public class MessaageMetaExtractService
extends JServiceFactorySupport<MessaageMetaExtractService>
implements JService ,JParser
{
	
	public static final JByteDecoder BYTE_DECODER=
			new JByteDecoder() {
				@Override
				public Object decode(byte[] bytes) throws Exception {
					return JJSON.get().parse(new String(bytes,"utf-8"), DefaultMessageMeta.class);
				}
			};
	
	public MessageMeta parse(byte[]  bytes)throws Exception{
		DefaultMessageMeta messageMeta= (DefaultMessageMeta) JMessageReceiverBuilder.get(bytes)
		.setByteDecoder(BYTE_DECODER).build().receive();
		messageMeta.setUrl(new URI(messageMeta.getUrl()).getPath());
		return messageMeta;
	}
	
	@Override
	public MessaageMetaExtractService getService() {
		return this;
	}

}
