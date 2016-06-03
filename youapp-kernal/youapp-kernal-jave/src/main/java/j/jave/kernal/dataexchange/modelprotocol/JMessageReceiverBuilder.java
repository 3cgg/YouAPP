package j.jave.kernal.dataexchange.modelprotocol;


public class JMessageReceiverBuilder {
	
	private byte[] bytes;
	
	private JByteDecoder byteDecoder;
	
	public  static JMessageReceiverBuilder get(byte[] bytes){
		JMessageReceiverBuilder protocolSendBuilder=  new JMessageReceiverBuilder();
		protocolSendBuilder.bytes=bytes;
		return protocolSendBuilder;
	}
	
	private JMessageReceiverBuilder() {
	}
	
	public JMessageReceiver build(){
		JMessageReceiver protocolReceiver=new JMessageReceiver(bytes);
		protocolReceiver.setByteDecoder(byteDecoder);
		return protocolReceiver;
	}
	
	public JMessageReceiverBuilder setByteDecoder(JByteDecoder byteDecoder) {
		this.byteDecoder = byteDecoder;
		return this;
	}
	
}
