package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.dataexchange.protocol.JProtocolConstants;
import j.jave.kernal.http.JHttpFactoryProvider;
import j.jave.kernal.http.JResponseHandler;

public class HttpClientSenderSender extends Sender {

	private static JResponseHandler<byte[]> responseHandler=new JResponseHandler<byte[]>(){
		public byte[] process(byte[] bytes) throws JResponseHandler.ProcessException {
			return bytes;
		};
	};
	
	
	protected byte[] doSend(Message message) throws Exception {
		byte[] data=getSenderData(message);
		byte[] bytes=(byte[]) JHttpFactoryProvider.getHttpFactory().getHttpPost()
				.setUrl(message.getUrl())
				.setEntry(data)
				.setResponseHandler(responseHandler)
				.setRetry(0)
				.putHead(JProtocolConstants.PROTOCOL_HEAD, message.getProtocol().name())
				.execute();
		return bytes;
	};
	
}
