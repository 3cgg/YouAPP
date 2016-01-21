package j.jave.framework.commons.utils;

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
	
	
}
