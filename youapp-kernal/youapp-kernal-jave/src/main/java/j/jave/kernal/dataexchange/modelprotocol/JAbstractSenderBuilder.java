package j.jave.kernal.dataexchange.modelprotocol;

@SuppressWarnings("unchecked")
public abstract class JAbstractSenderBuilder<T extends JAbstractSenderBuilder<T>> {

	protected JDefaultProtocolSender protocolSender;
	
	protected JProtocolByteHandler receiveHandler;
	
	protected JProtocolObjectHandler sendHandler;
	
	protected String serverHandler;
	
	protected String url;
	
	public abstract T build();
	
	
	public T setURL(String url){
		this.url=url;
		return (T) this;
	}
	
	public T setReceiveHandler(JProtocolByteHandler receiveHandler) {
		this.receiveHandler = receiveHandler;
		return (T) this;
	}
	
	public T setSendHandler(JProtocolObjectHandler sendHandler) {
		this.sendHandler = sendHandler;
		return (T) this;
	}
	
	public T setServerHandler(String serverHandler) {
		this.serverHandler = serverHandler;
		return (T) this;
	}
	
	public final <M> M send() {
		return this.protocolSender.send();
	}
	
	
	
}
