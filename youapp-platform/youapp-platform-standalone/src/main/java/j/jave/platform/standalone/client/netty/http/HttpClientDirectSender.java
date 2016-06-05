package j.jave.platform.standalone.client.netty.http;

import j.jave.kernal.dataexchange.channel.JDirectSender;
import j.jave.kernal.dataexchange.channel.JMessage;

public class HttpClientDirectSender extends JDirectSender {

	@Override
	protected byte[] doSend(JMessage message) throws Exception {
		ConnectionService connectionService= new OnceConnectionService();
		connectionService.connect(message.getUrl());
		return connectionService.request(message, null,getSenderData(message));
	}

}
