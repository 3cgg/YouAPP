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
	
	/**
	 * Return a String representation of an object's overall identity.
	 * @param obj the object (may be {@code null})
	 * @return the object's identity as String representation,
	 * or an empty String if the object was {@code null}
	 */
	public static String identityToString(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.getClass().getName() + "@" + getIdentityHexString(obj);
	}

	/**
	 * Return a hex String form of an object's identity hash code.
	 * @param obj the object
	 * @return the object's identity code in hex notation
	 */
	public static String getIdentityHexString(Object obj) {
		return Integer.toHexString(System.identityHashCode(obj));
	}
}
