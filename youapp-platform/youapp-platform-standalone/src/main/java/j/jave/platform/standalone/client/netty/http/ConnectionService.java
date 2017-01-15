package j.jave.platform.standalone.client.netty.http;

import java.util.Map;

import me.bunny.kernel.dataexchange.channel.JMessage;

interface ConnectionService {

	/**
	 * request and return the reply to the request.
	 * @param message
	 * @param heads head names
 	 * @param bytes  the content bytes
	 * @return
	 */
	byte[] request(JMessage message, Map<String, Object> heads,
			byte[] bytes) throws Exception;

	ConnectionService connect(String url) throws Exception;

	public boolean isActive();
	
	public ConnectionService await(int timeout);
	
	/**
	 * forward to {@link #await(int)} , await until the connection is active.
	 * @return
	 */
	public ConnectionService await();
	
}