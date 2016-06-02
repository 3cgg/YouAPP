package j.jave.platform.standalone.server.netty.http;

import j.jave.kernal.dataexchange.modelprotocol.JProtocolByteHandler;
import j.jave.kernal.dataexchange.modelprotocol.JProtocolReceiverBuilder;
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
	
	public static final JProtocolByteHandler PROTOCOL_BYTE_HANDLER=
			new JProtocolByteHandler() {
				@Override
				public Object handle(byte[] bytes) throws Exception {
					return JJSON.get().parse(new String(bytes,"utf-8"), DefaultMessageMeta.class);
				}
			};
	
	public MessageMeta parse(byte[]  bytes)throws Exception{
		DefaultMessageMeta messageMeta= (DefaultMessageMeta) JProtocolReceiverBuilder.get(bytes)
		.setProtocolByteHandler(PROTOCOL_BYTE_HANDLER).build().receive();
		messageMeta.setUrl(new URI(messageMeta.getUrl()).getPath());
		return messageMeta;
	}
	
	@Override
	public MessaageMetaExtractService getService() {
		return this;
	}

}
