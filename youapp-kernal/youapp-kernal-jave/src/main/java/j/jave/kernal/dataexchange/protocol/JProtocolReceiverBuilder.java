package j.jave.kernal.dataexchange.protocol;


public class JProtocolReceiverBuilder {
	
	private JProtocol protocol;
	
	private byte[] bytes;
	
	public  static JProtocolReceiverBuilder get(String protocol){
		return get(JProtocol.valueOf(protocol));
	}
	
	public  static JProtocolReceiverBuilder get(JProtocol protocol){
		JProtocolReceiverBuilder protocolSendBuilder=  new JProtocolReceiverBuilder();
		protocolSendBuilder.protocol=protocol;
		return protocolSendBuilder;
	}
	
	private JProtocolReceiverBuilder() {
	}
	
	public JProtocolReceiverBuilder setData(byte[] bytes){
		this.bytes=bytes;
		return this;
	}
	
	public JProtocolReceiver build(){
		JProtocolReceiver protocolReceiver=null;
		if(protocol==JProtocol.OBJECT){
			protocolReceiver=new JProtocolReceiver.ObjectProtocolReceiver(bytes);
		}
		else if(protocol==JProtocol.JSON){
			protocolReceiver=new JProtocolReceiver.JSONProtocolReceiver(bytes);
		}
		return protocolReceiver;
	}
	
}
