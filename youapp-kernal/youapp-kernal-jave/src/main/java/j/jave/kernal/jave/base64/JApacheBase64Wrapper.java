package j.jave.kernal.jave.base64;

import org.apache.commons.codec.binary.Base64;

public class JApacheBase64Wrapper implements JBase64 {
	
	private JApacheBase64Wrapper() {
	}

	private static JApacheBase64Wrapper apacheBase64Wrapper=new JApacheBase64Wrapper(); 
	
	public static JApacheBase64Wrapper get(){
		return apacheBase64Wrapper;
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
