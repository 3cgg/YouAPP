/**
 * 
 */
package j.jave.kernal.jave.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author J
 */
public abstract class JUniqueUtils {

	public final static String SEQUECE = "yyyyMMddHHmmssSSS";
	
	public static String unique() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * get time format of the format 'yyyyMMddHHmmssSSS'
	 * @return
	 */
	public static String sequence(){
		SimpleDateFormat oFormat = new SimpleDateFormat(SEQUECE);
		oFormat.setLenient(false);
		return oFormat.format(new Timestamp(new Date().getTime()));
	}
	
	
}
