package j.jave.kernal.streaming.netty.controller;

import j.jave.kernal.dataexchange.model.DefaultMessageMeta;
import j.jave.kernal.jave.base64.JBase64;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;

public class DefaultFastMessageMeta extends DefaultMessageMeta implements FastMessageMeta{

	/**
	 * the type describer of {@link #bytes()}
	 */
	private String className;
	
	protected static JBase64 base64Service=
			JBase64FactoryProvider.getBase64Factory().getBase64();
	
	private byte[]  bytes;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	@Override
	public String getData() {
		return base64Service.encodeBase64String(bytes);
	}
	
	@Override
	public String getDataEncoder() {
		return "kryo";
	}

	@Override
	public byte[] bytes() {
		return getBytes();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
