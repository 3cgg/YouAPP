/**
 * 
 */
package me.bunny.kernel._c.support._package;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import me.bunny.kernel._c.support.JProvider;
import me.bunny.kernel._c.support._package.JAbstractMethodFinder.JMethodFilter;

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
	 * VALUE : ANY expected values of {@link JFieldInfo}.
	 * @return
	 */
	public Map<Class<?>, List<T>> getClassFieldInfos() ;
	
}
