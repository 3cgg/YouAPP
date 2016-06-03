package j.jave.kernal.dataexchange.modelprotocol;

@SuppressWarnings("unchecked")
public abstract class JBaseSenderBuilder<T extends JBaseSenderBuilder<T>> {

	protected JDefaultMessageSender protocolSender;
	
	protected JByteDecoder receiveByteDecoder;
	
	protected JObjectEncoder sendObjectEncoder;
	
	protected String dataByteEncoder;
	
	protected String url;
	
	public abstract T build();
	
	
	public T setURL(String url){
		this.url=url;
		return (T) this;
	}
	
	public T setReceiveByteDecoder(JByteDecoder receiveByteDecoder) {
		this.receiveByteDecoder = receiveByteDecoder;
		return (T) this;
	}
	
	public T setSendObjectEncoder(JObjectEncoder sendObjectEncoder) {
		this.sendObjectEncoder = sendObjectEncoder;
		return (T) this;
	}
	
	public T setDataByteEncoder(String dataByteEncoder) {
		this.dataByteEncoder = dataByteEncoder;
		return (T) this;
	}
	
	public final <M> M send() {
		return this.protocolSender.send();
	}
	
	
	
}
