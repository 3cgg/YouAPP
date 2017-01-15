package me.bunny.kernel.dataexchange.channel;

import me.bunny.kernel.dataexchange.impl.JMessageHeadNames;
import me.bunny.kernel.http.JHttpFactoryProvider;
import me.bunny.kernel.http.JResponseHandler;

public class JHttpClientDirectSender extends JDirectSender {

	private static JResponseHandler<byte[]> responseHandler=new JResponseHandler<byte[]>(){
		public byte[] process(byte[] bytes) throws JResponseHandler.ProcessException {
			return bytes;
		};
	};
	
	
	protected byte[] doSend(JMessage message) throws Exception {
		byte[] data=getSenderData(message);
		byte[] bytes=(byte[]) JHttpFactoryProvider.getHttpFactory().getHttpPost()
				.setUrl(message.getUrl())
				.setEntry(data)
				.setResponseHandler(responseHandler)
				.setRetry(0)
				.putHead(JMessageHeadNames.DATA_EXCHNAGE_IDENTIFIER,JMessage.class.getName())
				.execute();
		return bytes;
	};
	
}
