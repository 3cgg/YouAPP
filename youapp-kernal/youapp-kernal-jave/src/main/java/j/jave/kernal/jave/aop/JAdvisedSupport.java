package j.jave.kernal.jave.aop;

import j.jave.kernal.jave.utils.JAssert;

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

	/**
	 * Canonical TargetSource when there's no target, and behavior is
	 * supplied by the advisors.
	 */
	public static final JTargetSource EMPTY_TARGET_SOURCE = JEmptyTargetSource.INSTANCE;


	/** Package-protected to allow direct access for efficiency */
	JTargetSource targetSource = EMPTY_TARGET_SOURCE;
	
	
	public void setTargetSource(JTargetSource targetSource) {
		this.targetSource = (targetSource != null ? targetSource : EMPTY_TARGET_SOURCE);
	}

	public JTargetSource getTargetSource() {
		return this.targetSource;
	}
	
	/**
	 * Return the target class behind the implementing object
	 * (typically a proxy configuration or an actual proxy).
	 * @return the target Class, or {@code null} if not known
	 */
	public Class<?> getTargetClass(){
		return this.targetSource.getTargetClass();
	}

	/**
	 * Set a target class to be proxied, indicating that the proxy
	 * should be castable to the given class.
	 * <p>Internally, an {@link org.springframework.aop.target.EmptyTargetSource}
	 * for the given target class will be used. The kind of proxy needed
	 * will be determined on actual creation of the proxy.
	 * <p>This is a replacement for setting a "targetSource" or "target",
	 * for the case where we want a proxy based on a target class
	 * (which can be an interface or a concrete class) without having
	 * a fully capable TargetSource available.
	 * @see #setTargetSource
	 * @see #setTarget
	 */
	public void setTargetClass(Class<?> targetClass) {
		this.targetSource = JEmptyTargetSource.forClass(targetClass);
	}
	
	public List<Class<?>> getInterfaces() {
		return interfaces;
	}

	/**
	 * Set the interfaces to be proxied.
	 */
	public void setInterfaces(Class<?>... interfaces) {
		JAssert.notNull(interfaces, "Interfaces must not be null");
		this.interfaces.clear();
		for (Class<?> ifc : interfaces) {
			addInterface(ifc);
		}
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

	/**
	 * Add a new proxied interface.
	 * @param intf the additional interface to proxy
	 */
	public void addInterface(Class<?> intf) {
		JAssert.notNull(intf, "Interface must not be null");
		if (!intf.isInterface()) {
			throw new IllegalArgumentException("[" + intf.getName() + "] is not an interface");
		}
		if (!this.interfaces.contains(intf)) {
			this.interfaces.add(intf);
			adviceChanged();
		}
	}
	
	/**
	 * Invoked when advice has changed.
	 */
	protected void adviceChanged() {
		if(this.methodCache!=null)
			this.methodCache.clear();
	}

	/**
	 * Return the interfaces proxied by the AOP proxy.
	 * <p>Will not include the target class, which may also be proxied.
	 */
	public Class<?>[] getProxiedInterfaces(){
		return this.interfaces.toArray(new Class<?>[this.interfaces.size()]);
	}

	/**
	 * Determine whether the given interface is proxied.
	 * @param intf the interface to check
	 */
	public boolean isInterfaceProxied(Class<?> intf){
		for (Class<?> proxyIntf : this.interfaces) {
			if (intf.isAssignableFrom(proxyIntf)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Build a configuration-only copy of this AdvisedSupport,
	 * replacing the TargetSource
	 */
	JAdvisedSupport getConfigurationOnlyCopy() {
		JAdvisedSupport copy = new JAdvisedSupport();
		copy.copyFrom(this);
		copy.targetSource = JEmptyTargetSource.forClass(getTargetClass(), getTargetSource().isStatic());
//		copy.advisorChainFactory = this.advisorChainFactory;
		copy.interfaces = this.interfaces;
//		copy.advisors = this.advisors;
//		copy.updateAdvisorArray();
		return copy;
	}
}
