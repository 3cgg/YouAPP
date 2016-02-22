/**
 * 
 */
package j.jave.kernal.jave.support.detect;

import j.jave.kernal.jave.support.detect.JMethodDetector.JMethodFilter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * collect and expose method information. 
 * @author J
 */
public interface JFieldInfoProvider <T> extends JProvider{

	public static interface JFieldInfoGen<T>{
		/**
		 * get method information , if all {@code filter()}  in the @see {@link JMethodFilter} return false. 
		 * @param method
		 * @param classIncudeMethod
		 * @return
		 */
		T getInfo(Field field,Class<?> classIncudeField);
	}
	
	/**
	 * get field Info. ANY expected value of {@link JFieldInfo}
	 * @return
	 */
	public List<T> getFieldInfos() ;
	
	/**
	 * 
	 * KEY : class type . 
	 * VALUE : ANY expected value of {@link JFieldInfo}.
	 * @return
	 */
	public Map<Class<?>, List<T>> getClassFieldInfos() ;
	
}
