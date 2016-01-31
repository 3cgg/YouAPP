/**
 * 
 */
package j.jave.kernal.jave.utils;

/**
 * @author J
 */
public abstract class JNumberUtils {

	/**
	 * compare number ,  before one is larger or equal after one 
	 * @param before
	 * @param after
	 * @return
	 */
	public static boolean gtEqualNumber(Object before,Object after){

		if(before==null&&after==null) return true;

		if(before==null) return false;

		if(after==null) return true;

		try{
			double beforeValue=Double.valueOf(String.valueOf(before));
			double afterValue=Double.valueOf(String.valueOf(after));
			return beforeValue>=afterValue;
		}catch (Exception e) {
			throw new JUtilException(e);
		}
	}
	
	/**
	 * compare number ,  before one is smaller or equal after one 
	 * @param before
	 * @param after
	 * @return
	 */
	public static boolean ltEqualNumber(Object before,Object after){

		if(before==null&&after==null) return true;

		if(before==null) return false;

		if(after==null) return true;

		try{
			double beforeValue=Double.valueOf(String.valueOf(before));
			double afterValue=Double.valueOf(String.valueOf(after));
			return beforeValue<=afterValue;
		}catch (Exception e) {
			throw new JUtilException(e);
		}
	}
	
	
	/**
	 * test the {@param string} is number or not.
	 * @param string
	 * @return
	 */
	public static boolean isNumber(String string){
		try{
			Double.valueOf(string);
		}catch (NumberFormatException  e) {
			return false;
		}
		return true;
	}
	
	/**
	 * parse double. 0 return if {@param value} is empty 
	 * @param value
	 * @return
	 */
	public static double toDouble(String value){
		if(JStringUtils.isNullOrEmpty(value)){
			return 0;
		}else{
			return Double.valueOf(value).doubleValue();
		}
	}
	
	/**
	 * parse integer. 0 return if {@param value} is empty 
	 * @param value
	 * @return
	 */
	public static int toInt(String value){
		if(JStringUtils.isNullOrEmpty(value)){
			return 0;
		}else{
			return Integer.valueOf(value).intValue();
		}
	}
	/**
	 * parse long. 0 return if {@param value} is empty 
	 * @param value
	 * @return
	 */
	public static long toLong(String value){
		if(JStringUtils.isNullOrEmpty(value)){
			return 0;
		}else{
			return Long.valueOf(value).longValue();
		}
	}
	
	
	
}
