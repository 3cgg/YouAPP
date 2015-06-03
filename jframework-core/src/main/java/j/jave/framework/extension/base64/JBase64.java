package j.jave.framework.extension.base64;

import j.jave.framework.servicehub.JService;

public interface JBase64 extends JService {

	byte[] decodeBase64(String string);
	
	String encodeBase64String(byte[] bytes);
	
	String encodeBase64URLSafeString(byte[] bytes);
	
}
