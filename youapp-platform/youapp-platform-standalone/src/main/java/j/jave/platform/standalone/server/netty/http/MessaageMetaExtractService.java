package j.jave.platform.standalone.server.netty.http;

import java.net.URI;

import me.bunny.kernel.dataexchange.impl.JByteDecoder;
import me.bunny.kernel.dataexchange.impl.JDefaultMessageMetaReceiverBuilder;
import me.bunny.kernel.dataexchange.model.DefaultMessageMeta;
import me.bunny.kernel.dataexchange.model.MessageMeta;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.service.JService;
import me.bunny.kernel.jave.support.parser.JParser;

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
		DefaultMessageMeta messageMeta= (DefaultMessageMeta) 
				JDefaultMessageMetaReceiverBuilder.get(bytes).build().receive();
		messageMeta.setUrl(new URI(messageMeta.getUrl()).getPath());
		return messageMeta;
	}
	
	@Override
	protected MessaageMetaExtractService doGetService() {
		return this;
	}

}
