package j.jave.framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public abstract class JStringUtils {

	private static final Logger LOGGER=LoggerFactory.getLogger(JStringUtils.class);
	
	/**
	 * extract all bytes from the {@link InputStream}. 	
	 * @param input
	 * @return
	 * @throws IOException	
	 */
	public static byte[] getBytes(InputStream input) {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int n = 0;
	    try {
			while (-1 != (n = input.read(buffer))) {
			    output.write(buffer, 0, n);
			}
		} catch (IOException e) {
			LOGGER.warn("", e);
			throw new UtilException(e);
		}
	    return output.toByteArray();
	}
	
	/**
	 * check whether the length of {@param string} exceeds the {@param size}.
	 * @param string
	 * @param size
	 * @return 
	 */
	public static boolean exceedLength(String string, int size) {
		if (string == null)
			return false;
		else
			return string.length() > size;
	}

	

	/**
	 * this method always be invoked before stringtobytes public static byte[]
	 * stringtobytes(String string)
	 *
	 * @param bytes
	 * @return
	 */
	public static String bytestoBASE64String(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	/**
	 * this method always be invoked after bytestoString public static String
	 * bytestoString(byte[] bytes)
	 *
	 * @param string			
	 * @return
	 */
	public static byte[] base64stringtobytes(String string) {
		try {
			return new BASE64Decoder().decodeBuffer(string);
		} catch (IOException e) {
			LOGGER.warn("", e);
			throw new UtilException(e);
		}
	}
	
	/**
	 * return true if the {@param before} is larger or equal than {@param after}
	 * <p>compare mechanism on {@link Comparable} 
	 * @param before
	 * @param after
	 * @return		
	 */
	public static boolean gtEqualString(Object before,Object after){

		if(before==null&&after==null) return true;

		if(before==null) return false;

		if(after==null) return true;

		return String.valueOf(before).compareTo(String.valueOf(after))>=0;
	}

	/**
	 * return true if the {@param before} is smaller or equal than {@param after}
	 * <p>compare mechanism on {@link Comparable} 
	 * @param before
	 * @param after
	 * @return		
	 */
	public static boolean ltEqualString(Object before,Object after){

		if(before==null&&after==null) return true;

		if(before==null) return false;

		if(after==null) return true;

		return String.valueOf(before).compareTo(String.valueOf(after))<=0;
	}
	
	/**
	 * check the {@param string} is null or empty . 
	 * @param string
	 * @return
	 */
	public static boolean isNullOrEmpty(String string){
		return string==null||"".equals(string.trim());
	}
	
	/**
	 * check the {@param string} is not null or empty . 
	 * @param string
	 * @return
	 */
	public static boolean isNotNullOrEmpty(String string){
		return string!=null&&!"".equals(string.trim());
	}
	
	/**
	 * convert {@param object} to string, empety returned if the object is null . 
	 * @see String.valueOf() 
	 * @param object
	 * @return
	 */
	public static String toString(Object object){
		if(object==null){
			return "";
		}
		else{
			return String.valueOf(object);
		}
	}
	
	
}
