/**
 * 
 */
package j.jave.framework.support.detect;

import j.jave.framework.support.detect.JMethodDetect.JMethodFilter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author J
 */
public interface JMethodInfoProvider <T>{

	public static interface JMethodInfo<T>{
		/**
		 * get method information , if all {@code filter()}  in the @see {@link JMethodFilter} return false. 
		 * @param method
		 * @param classIncudeMethod
		 * @return
		 */
		T getInfo(Method method,Class<?> classIncudeMethod);
	}
	
	/**
	 * get Method Info. ANY expected value from {@link JMethodInfo}
	 * @return
	 */
	public List<T> getMethodInfos() ;
	
	/**
	 * 
	 * KEY : class type . 
	 * VALUE : ANY expected value from {@link JMethodInfo}.
	 * @return
	 */
	public Map<Class<?>, List<T>> getClassMethodInfos() ;
	
}
