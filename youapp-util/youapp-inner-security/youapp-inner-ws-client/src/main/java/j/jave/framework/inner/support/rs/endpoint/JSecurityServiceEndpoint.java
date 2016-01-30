package j.jave.framework.inner.support.rs.endpoint;


public interface JSecurityServiceEndpoint {

	/**
	 * the value should be the same as the <code>java.nio.file.Path</code>
	 */
	public static final String prefix="/security";
	
	public static final String encryptOnDESede="encryptOnDESede/plainValue/{plain}";
	
	public static final String decryptOnDESede="decryptOnDESede/encryptedValue/{encrypted}";

	public static final String encryptOnMD5="encryptOnMD5/plainValue";
	
}
