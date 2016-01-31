package j.jave.kernal.jave.model.support.interceptor;


/**
 * 
 * @author J
 *
 * @param <T>
 */
public interface JModelIntercepter <T> {

	public abstract T intercept(JModelInvocation<T> modelInvocation);
	
}
