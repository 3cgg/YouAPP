package j.jave.platform.standalone.client.netty.http;

import j.jave.kernal.dataexchange.channel.JDirectSender;
import j.jave.kernal.dataexchange.channel.JMessage;

public class HttpClientDirectSender extends JDirectSender {

	@Override
	protected byte[] doSend(JMessage message) throws Exception {
		ConnectionService connectionService= ConnectionService.get(message.getUrl());
		connectionService.connect();
		return connectionService.request(message, getSenderData(message));
	}

}
