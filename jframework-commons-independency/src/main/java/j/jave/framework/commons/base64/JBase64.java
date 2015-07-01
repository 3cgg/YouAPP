package j.jave.framework.commons.base64;


public interface JBase64 {

	byte[] decodeBase64(String string);
	
	String encodeBase64String(byte[] bytes);
	
	String encodeBase64URLSafeString(byte[] bytes);
	
}
