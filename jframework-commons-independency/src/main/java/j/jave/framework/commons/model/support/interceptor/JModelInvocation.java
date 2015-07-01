package j.jave.framework.commons.model.support.interceptor;

/**
 * 
 * @author J
 *
 * @param <T>
 */
public interface JModelInvocation<T> {

	/**
	 * progress of executing intercepters
	 * @return
	 */
	public T proceed();
	
}
