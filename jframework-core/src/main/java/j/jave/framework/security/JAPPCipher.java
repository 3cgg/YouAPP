package j.jave.framework.security;

import j.jave.framework.utils.JUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * DESede Ciper 
 * @author J
 *
 */
public class JAPPCipher {

	private final static String DESEDE = "DESede";
	private final static byte[] algorithmprama = { 9, 99, 88, 1, -1, -88, -99, -9 };
	
	private Cipher encryptCipher;
	private Cipher decryptCipher;
	
	private static JAPPCipher appCipher;
	
	private JAPPCipher(){}
	/**
	 * get default cipher , key is configured in properties.
	 * @return
	 */
	public static JAPPCipher get(){
		if(appCipher==null){
			appCipher=new JAPPCipher();
			String key="j.jave.framework.components.security";
			appCipher=get(key);
		}
		return appCipher;
	}
	
	/**
	 * a initialized new cipher returned to the caller every time.  
	 * @param key
	 * @return
	 */
	public static JAPPCipher get(String key){
		try{
			JAPPCipher appCipher=new JAPPCipher();
			appCipher.encryptCipher=encrypt(key.getBytes());
			appCipher.decryptCipher=decrypt(key.getBytes());
			return appCipher;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String encrypt(String plain) {
		try{
			return JUtils.bytestoBASE64String(this.encryptCipher.doFinal(plain.getBytes()));
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String decrypt(String encrypted) {
		try{
			return new String(this.decryptCipher.doFinal(JUtils.base64stringtobytes(encrypted)));
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Cipher encrypt(byte[] key) throws Exception {
		IvParameterSpec ivprama = new IvParameterSpec(algorithmprama);
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DESEDE);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey, ivprama);
		return cipher;
	}

	private static Cipher decrypt(byte[] key) throws Exception {
		IvParameterSpec ivprama = new IvParameterSpec(algorithmprama);
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DESEDE);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, securekey, ivprama);
		return cipher;
	}
}
