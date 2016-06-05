package j.jave.platform.standalone.client.netty.http;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import j.jave.kernal.dataexchange.channel.JMessage;

import java.util.HashMap;
import java.util.Map;

public class OnceConnectionService implements ConnectionService {

	private DefaultConnectionService defaultConnectionService;
	
	@Override
	public byte[] request(JMessage message, Map<String, Object> heads,
			byte[] bytes) throws Exception {
		if(heads==null){
			heads=new HashMap<String, Object>();
		}
		heads.put(HttpHeaderNames.CONNECTION.toString(),HttpHeaderValues.CLOSE.toString());
		return defaultConnectionService.request(message, heads, bytes);
	}

	@Override
	public ConnectionService connect(String url) throws Exception {
		defaultConnectionService=DefaultConnectionService.get(url);
		defaultConnectionService.connect();
		return this;
	}

	
	
}
