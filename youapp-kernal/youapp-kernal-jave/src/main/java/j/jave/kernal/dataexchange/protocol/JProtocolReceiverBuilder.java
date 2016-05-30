package j.jave.kernal.dataexchange.protocol;


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
//		if(protocol==JProtocol.OBJECT){
//			protocolReceiver=new JProtocolReceiver.ObjectProtocolReceiver(bytes);
//		}
//		else if(protocol==JProtocol.JSON){
//			protocolReceiver=new JProtocolReceiver.JSONProtocolReceiver(bytes);
//		}
		protocolReceiver.setProtocolByteHandler(protocolByteHandler);
		return protocolReceiver;
	}
	
	public JProtocolReceiverBuilder setProtocolByteHandler(JProtocolByteHandler protocolByteHandler) {
		this.protocolByteHandler = protocolByteHandler;
		return this;
	}
	
}
