package j.jave.kernal.dataexchange.modelprotocol;


public class JProtocolReceiverBuilder {
	
	private byte[] bytes;
	
	private JProtocolByteHandler protocolByteHandler;
	
	public  static JProtocolReceiverBuilder get(byte[] bytes){
		JProtocolReceiverBuilder protocolSendBuilder=  new JProtocolReceiverBuilder();
		protocolSendBuilder.bytes=bytes;
		return protocolSendBuilder;
	}
	
	private JProtocolReceiverBuilder() {
	}
	
	public JProtocolReceiver build(){
		JProtocolReceiver protocolReceiver=new JProtocolReceiver(bytes);
		protocolReceiver.setProtocolByteHandler(protocolByteHandler);
		return protocolReceiver;
	}
	
	public JProtocolReceiverBuilder setProtocolByteHandler(JProtocolByteHandler protocolByteHandler) {
		this.protocolByteHandler = protocolByteHandler;
		return this;
	}
	
}
