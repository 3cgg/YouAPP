package me.bunny.kernel._c.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import me.bunny.kernel._c.utils.JStringUtils;


/**
 * DESede Cipher 
 * @author J
 *
 */
public class JDESedeCipher {

	private final static String DESEDE = "DESede";
	private final static byte[] algorithmprama = { 9, 99, 88, 1, -1, -88, -99, -9 };
	
	private Cipher encryptCipher;
	private Cipher decryptCipher;
	
	private static JDESedeCipher appCipher;
	
	private JDESedeCipher(){}
	/**
	 * get default cipher , key is default as the class full name.
	 * <strong>Note that the method is only for test, as the key is unsafe.</strong>
	 * @return
	 */
	public static JDESedeCipher get(){
		if(appCipher==null){
			appCipher=new JDESedeCipher();
			String key="j.jave.framework.commons.security.JDESedeCipher";
			appCipher=get(key);
		}
		return appCipher;
	}
	
	/**
	 * an initialized new cipher returned to the caller every time.  
	 * <strong>The method should be used in the release production.</strong>
	 * @param key
	 * @return
	 */
	public static JDESedeCipher get(String key){
		try{
			JDESedeCipher appCipher=new JDESedeCipher();
			appCipher.encryptCipher=encrypt(key.getBytes());
			appCipher.decryptCipher=decrypt(key.getBytes());
			return appCipher;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String encrypt(String plain) {
		try{
			return JStringUtils.bytestoBASE64String(this.encryptCipher.doFinal(plain.getBytes()));
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String decrypt(String encrypted) {
		try{
			return new String(this.decryptCipher.doFinal(JStringUtils.base64stringtobytes(encrypted)));
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
