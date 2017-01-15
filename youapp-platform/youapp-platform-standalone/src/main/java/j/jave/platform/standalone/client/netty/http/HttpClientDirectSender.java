package j.jave.platform.standalone.client.netty.http;

import me.bunny.kernel.dataexchange.channel.JDirectSender;
import me.bunny.kernel.dataexchange.channel.JMessage;

public class HttpClientDirectSender extends JDirectSender {

	@Override
	protected byte[] doSend(JMessage message) throws Exception {
//		ConnectionService connectionService= new OnceConnectionService(message.getUrl());
//		connectionService.connect(message.getUrl());
//		connectionService.await();
		
		ConnectionService connectionService= ConnectionPoolService.get(message.getUrl());
		
		return connectionService.request(message, null,getSenderData(message));
	}

}
