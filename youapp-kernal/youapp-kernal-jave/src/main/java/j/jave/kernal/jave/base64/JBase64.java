package j.jave.kernal.jave.base64;


public interface JBase64 {

	byte[] decodeBase64(String base64String);
	
	String encodeBase64String(byte[] bytes);
	
	String encodeBase64URLSafeString(byte[] bytes);
	
}
