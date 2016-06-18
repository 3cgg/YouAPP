package j.jave.kernal.jave.utils;

public class JLangUtils {
	
	/**
     * Check if two objects are equal.
     *
     * @param obj1 first object to compare, may be {@code null}
     * @param obj2 second object to compare, may be {@code null}
     * @return {@code true} if the objects are equal or both null
     */
	public static boolean equals(final Object obj1, final Object obj2 ){
		return obj1 == null ? obj2 == null : obj1.equals(obj2);
	}
	
	/**
	 * parse the input string(case sensitive) to boolean, <strong>rule: on/true==true; off/false==false</strong>
	 * @param string  on/off , false/true 
	 * @return
	 */
	public static boolean booleanValue(String string){
		JAssert.isNotEmpty(string);
		if("on".equals(string.trim())){
			return true;
		}
		if("off".equals(string.trim())){
			return false;
		}
		return Boolean.valueOf(string);
	}
	
}
