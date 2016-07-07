package j.jave.kernal.jave.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JAdvisedSupport extends JProxyConfig {

	/** Cache with Method as key and advisor chain List as value */
	private transient Map<Method, List<Object>> methodCache;

	
	/**
	 * Interfaces to be implemented by the proxy. Held in List to keep the order
	 * of registration, to create JDK proxy with specified order of interfaces.
	 */
	private List<Class<?>> interfaces = new ArrayList<Class<?>>();

	private List<Object> interceptors=new ArrayList<Object>();
	
	public List<Class<?>> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<Class<?>> interfaces) {
		this.interfaces = interfaces;
	}

	public List<Object> getInterceptors() {
		return interceptors;
	}

	public void setInterceptors(List<Object> interceptors) {
		this.interceptors = interceptors;
	}
	
	
	/**
	 * Determine a list of {@link org.aopalliance.intercept.MethodInterceptor} objects
	 * for the given method, based on this configuration.
	 * @param method the proxied method
	 * @param targetClass the target class
	 * @return List of MethodInterceptors (may also include InterceptorAndDynamicMethodMatchers)
	 */
	public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
//		MethodCacheKey cacheKey = new MethodCacheKey(method);
//		List<Object> cached = this.methodCache.get(cacheKey);
//		if (cached == null) {
//			cached = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(
//					this, method, targetClass);
//			this.methodCache.put(cacheKey, cached);
//		}
		return interceptors;
	}

	
	
}
