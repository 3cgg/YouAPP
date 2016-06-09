package j.jave.platform.standalone.client.netty.http;

import j.jave.kernal.dataexchange.channel.JMessage;

import java.util.Map;

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