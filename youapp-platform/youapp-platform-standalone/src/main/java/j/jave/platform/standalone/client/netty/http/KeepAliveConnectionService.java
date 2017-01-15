package j.jave.platform.standalone.client.netty.http;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import me.bunny.kernel.dataexchange.channel.JMessage;

import java.util.HashMap;
import java.util.Map;

public class KeepAliveConnectionService extends AbstractConnectionService {
	
	public KeepAliveConnectionService(String url) {
		super(url);
	}

	@Override
	public byte[] request(JMessage message, Map<String, Object> heads,
			byte[] bytes) throws Exception {
		if(heads==null){
			heads=new HashMap<String, Object>();
		}
		heads.put(HttpHeaderNames.CONNECTION.toString(),HttpHeaderValues.KEEP_ALIVE.toString());
		return defaultConnectionService.request(message, heads, bytes);
	}

	@Override
	public ConnectionService connect(String url) throws Exception {
		defaultConnectionService.connect();
		return this;
	}

	
	
}
