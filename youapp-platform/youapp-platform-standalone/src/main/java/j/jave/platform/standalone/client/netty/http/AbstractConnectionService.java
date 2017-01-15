package j.jave.platform.standalone.client.netty.http;

import java.util.HashMap;
import java.util.Map;

import me.bunny.kernel.dataexchange.channel.JMessage;


public abstract class AbstractConnectionService implements ConnectionService {

	protected DefaultConnectionService defaultConnectionService;

	public AbstractConnectionService(String url) {
		defaultConnectionService=new DefaultConnectionService(url);
	}
	
	@Override
	public byte[] request(JMessage message, Map<String, Object> heads,
			byte[] bytes) throws Exception {
		return defaultConnectionService.request(message, heads==null?new HashMap<String, Object>():heads, bytes);
	}
	
	
	@Override
	public ConnectionService connect(String url) throws Exception {
		defaultConnectionService.connect();
		return this;
	}

	@Override
	public boolean isActive() {
		return defaultConnectionService.isActive();
	}
	
	@Override
	public ConnectionService await(int timeout) {
		defaultConnectionService.await(timeout);
		return this;
	}
	
	@Override
	public ConnectionService await() {
		return await(0);
	}
	

	
}
