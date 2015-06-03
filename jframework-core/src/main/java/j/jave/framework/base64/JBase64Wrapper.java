package j.jave.framework.base64;

import j.jave.framework.extension.base64.JBase64;

import org.apache.commons.codec.binary.Base64;

public class JBase64Wrapper implements JBase64 {

	private Base64 base64;
	
	public JBase64Wrapper() {
		
	}

	@Override
	public byte[] decodeBase64(String string) {
		return Base64.decodeBase64(string);
	}

	@Override
	public String encodeBase64String(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	@Override
	public String encodeBase64URLSafeString(byte[] bytes) {
		return Base64.encodeBase64URLSafeString(bytes);
	}

	
	
}
