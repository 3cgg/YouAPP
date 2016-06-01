package j.jave.platform.standalone.client.netty.http;

import j.jave.kernal.dataexchange.channel.Message;
import j.jave.kernal.dataexchange.channel.Sender;

public class HttpClientSender extends Sender {

	@Override
	protected byte[] doSend(Message message) throws Exception {
		ConnectionService connectionService= ConnectionService.get(message.getUrl());
		connectionService.request(message, getSenderData(message));
		return null;
	}

}
