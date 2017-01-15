package me.bunny.kernel.dataexchange.impl;

import me.bunny.kernel.dataexchange.model.DefaultMessageMeta;

public class JDefaultMessageMetaSenderBuilder {

	protected JDefaultMessageMetaSender protocolSender;
	
	protected JByteDecoder receiveByteDecoder;
	
	protected DefaultMessageMeta defaultMessageMeta;
	
	public JDefaultMessageMetaSenderBuilder setURL(String url){
		defaultMessageMeta.setUrl(url);
		return this;
	}
	
	public JDefaultMessageMetaSenderBuilder setReceiveByteDecoder(JByteDecoder receiveByteDecoder) {
		this.receiveByteDecoder = receiveByteDecoder;
		return this;
	}
	
	public JDefaultMessageMetaSenderBuilder setDataByteEncoder(String dataByteEncoder) {
		defaultMessageMeta.setDataEncoder(dataByteEncoder);
		return this;
	}
	
	public final <M> M send() {
		return this.protocolSender.send();
	}
	
	public  static JDefaultMessageMetaSenderBuilder get(){
		JDefaultMessageMetaSenderBuilder protocolSendBuilder=  new JDefaultMessageMetaSenderBuilder();
		protocolSendBuilder.defaultMessageMeta=new DefaultMessageMeta();
		return protocolSendBuilder;
	}
	
	private JDefaultMessageMetaSenderBuilder() {
	}
	
	/**
	 * put the base64 string of any object
	 * @param data the base64 string
	 * @return
	 */
	public JDefaultMessageMetaSenderBuilder setBase64String(String base64String){
		defaultMessageMeta.setData(base64String);
		return this;
	}
	
	/**
	 * set the encoder that generates the byte of {@link #setBase64String(String)}
	 * @param dataEncoder
	 * @return
	 */
	public JDefaultMessageMetaSenderBuilder setDataEncoderForBase64StringByte(String dataEncoder){
		defaultMessageMeta.setDataEncoder(dataEncoder);
		return this;
	}
	
	public JDefaultMessageMetaSenderBuilder build(){
		protocolSender=new JDefaultMessageMetaSender(defaultMessageMeta);
		protocolSender.setReceiveByteDecoder(receiveByteDecoder);
		return this;
	}
	
}
