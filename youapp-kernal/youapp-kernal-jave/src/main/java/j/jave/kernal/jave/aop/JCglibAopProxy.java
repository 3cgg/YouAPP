package j.jave.kernal.jave.aop;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.utils.JAssert;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.sf.cglib.core.CodeGenerationException;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Dispatcher;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

import org.aopalliance.intercept.MethodInvocation;

public class JCglibAopProxy implements JAopProxy {

	/** We use a static Log to avoid serialization issues */
	private static final JLogger logger = JLoggerFactory.getLogger(JCglibAopProxy.class);

	// Constants for CGLIB callback array indices
	private static final int AOP_PROXY = 0;
	private static final int INVOKE_TARGET = 1;
	private static final int NO_OVERRIDE = 2;
	private static final int DISPATCH_TARGET = 3;
	private static final int DISPATCH_ADVISED = 4;
	private static final int INVOKE_EQUALS = 5;
	private static final int INVOKE_HASHCODE = 6;
	
	/** The configuration used to configure this proxy */
	protected final JAdvisedSupport advised;

	protected Object[] constructorArgs;

	protected Class<?>[] constructorArgTypes;
	
	/** Dispatcher used for methods on Advised */
	private final transient JAdvisedDispatcher advisedDispatcher;

	private transient Map<String, Integer> fixedInterceptorMap;

	private transient int fixedInterceptorOffset;
	
	/** Keeps track of the Classes that we have validated for final methods */
	private static final Map<Class<?>, Boolean> validatedClasses = new WeakHashMap<Class<?>, Boolean>();
	
	/**
	 * Create a new CglibAopProxy for the given AOP configuration.
	 * @param config the AOP configuration as AdvisedSupport object
	 * @throws AopConfigException if the config is invalid. We try to throw an informative
	 * exception in this case, rather than let a mysterious failure happen later.
	 */
	public JCglibAopProxy(JAdvisedSupport config) throws JAopConfigException {
		JAssert.notNull(config, "AdvisedSupport must not be null");
//		if (config.getAdvisors().length == 0 && config.getTargetSource() == AdvisedSupport.EMPTY_TARGET_SOURCE) {
//			throw new AopConfigException("No advisors and no TargetSource specified");
//		}
		this.advised = config;
		this.advisedDispatcher = new JAdvisedDispatcher(this.advised);
	}
	
	@Override
	public Object getProxy() {
		return getProxy(null);
	}

	@Override
	public Object getProxy(ClassLoader classLoader) {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating CGLIB proxy: target source is " + this.advised.getTargetSource());
		}

		try {
			Class<?> rootClass = this.advised.getTargetClass();
			JAssert.state(rootClass != null, "Target class must be available for creating a CGLIB proxy");

			Class<?> proxySuperClass = rootClass;
			if (JClassUtils.isCglibProxyClass(rootClass)) {
				proxySuperClass = rootClass.getSuperclass();
				Class<?>[] additionalInterfaces = rootClass.getInterfaces();
				for (Class<?> additionalInterface : additionalInterfaces) {
					this.advised.addInterface(additionalInterface);
				}
			}

			// Validate the class, writing log messages as necessary.
			validateClassIfNecessary(proxySuperClass, classLoader);

			// Configure CGLIB Enhancer...
			Enhancer enhancer = createEnhancer();
			if (classLoader != null) {
				enhancer.setClassLoader(classLoader);
//				if (classLoader instanceof SmartClassLoader &&
//						((SmartClassLoader) classLoader).isClassReloadable(proxySuperClass)) {
//					enhancer.setUseCache(false);
//				}
			}
			enhancer.setSuperclass(proxySuperClass);
			enhancer.setInterfaces(JAopUtils.completeProxiedInterfaces(this.advised));
//			enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
//			enhancer.setStrategy(new ClassLoaderAwareUndeclaredThrowableStrategy(classLoader));

			Callback[] callbacks = getCallbacks(rootClass);
			Class<?>[] types = new Class<?>[callbacks.length];
			for (int x = 0; x < types.length; x++) {
				types[x] = callbacks[x].getClass();
			}
			// fixedInterceptorMap only populated at this point, after getCallbacks call above
			enhancer.setCallbackFilter(new ProxyCallbackFilter(
					this.advised.getConfigurationOnlyCopy(), this.fixedInterceptorMap, this.fixedInterceptorOffset));
			enhancer.setCallbackTypes(types);

			// Generate the proxy class and create a proxy instance.
			return createProxyClassAndInstance(enhancer, callbacks);
		}
		catch (CodeGenerationException ex) {
			throw new JAopConfigException("Could not generate CGLIB subclass of class [" +
					this.advised.getTargetClass() + "]: " +
					"Common causes of this problem include using a final class or a non-visible class",
					ex);
		}
		catch (IllegalArgumentException ex) {
			throw new JAopConfigException("Could not generate CGLIB subclass of class [" +
					this.advised.getTargetClass() + "]: " +
					"Common causes of this problem include using a final class or a non-visible class",
					ex);
		}
		catch (Exception ex) {
			// TargetSource.getTarget() failed
			throw new JAopConfigException("Unexpected AOP exception", ex);
		}
	}
	
	/**
	 * Checks to see whether the supplied {@code Class} has already been validated and
	 * validates it if not.
	 */
	private void validateClassIfNecessary(Class<?> proxySuperClass, ClassLoader proxyClassLoader) {
		if (logger.isInfoEnabled()) {
			synchronized (validatedClasses) {
				if (!validatedClasses.containsKey(proxySuperClass)) {
					doValidateClass(proxySuperClass, proxyClassLoader);
					validatedClasses.put(proxySuperClass, Boolean.TRUE);
				}
			}
		}
	}
	
	/**
	 * Checks for final methods on the given {@code Class}, as well as package-visible
	 * methods across ClassLoaders, and writes warnings to the log for each one found.
	 */
	private void doValidateClass(Class<?> proxySuperClass, ClassLoader proxyClassLoader) {
		if (Object.class != proxySuperClass) {
			Method[] methods = proxySuperClass.getDeclaredMethods();
			for (Method method : methods) {
				int mod = method.getModifiers();
				if (!Modifier.isStatic(mod)) {
					if (Modifier.isFinal(mod)) {
						logger.info("Unable to proxy method [" + method + "] because it is final: " +
								"All calls to this method via a proxy will NOT be routed to the target instance.");
					}
					else if (!Modifier.isPublic(mod) && !Modifier.isProtected(mod) && !Modifier.isPrivate(mod) &&
							proxyClassLoader != null && proxySuperClass.getClassLoader() != proxyClassLoader) {
						logger.info("Unable to proxy method [" + method + "] because it is package-visible " +
								"across different ClassLoaders: All calls to this method via a proxy will " +
								"NOT be routed to the target instance.");
					}
				}
			}
			doValidateClass(proxySuperClass.getSuperclass(), proxyClassLoader);
		}
	}

	protected Object createProxyClassAndInstance(Enhancer enhancer, Callback[] callbacks) {
		enhancer.setInterceptDuringConstruction(false);
		enhancer.setCallbacks(callbacks);
		return (this.constructorArgs != null ?
				enhancer.create(this.constructorArgTypes, this.constructorArgs) :
				enhancer.create());
	}
	
	private Callback[] getCallbacks(Class<?> rootClass) throws Exception {
		// Parameters used for optimisation choices...
		boolean exposeProxy = this.advised.isExposeProxy();
		boolean isFrozen = this.advised.isFrozen();
		boolean isStatic = this.advised.getTargetSource().isStatic();

		// Choose an "aop" interceptor (used for AOP calls).
		Callback aopInterceptor = new JDynamicAdvisedInterceptor(this.advised);

		// Choose a "straight to target" interceptor. (used for calls that are
		// unadvised but can return this). May be required to expose the proxy.
		Callback targetInterceptor;
		if (exposeProxy) {
			targetInterceptor = isStatic ?
					new JStaticUnadvisedExposedInterceptor(this.advised.getTargetSource().getTarget()) :
					new JDynamicUnadvisedExposedInterceptor(this.advised.getTargetSource());
		}
		else {
			targetInterceptor = isStatic ?
					new JStaticUnadvisedInterceptor(this.advised.getTargetSource().getTarget()) :
					new JDynamicUnadvisedInterceptor(this.advised.getTargetSource());
		}

		// Choose a "direct to target" dispatcher (used for
		// unadvised calls to static targets that cannot return this).
		Callback targetDispatcher = isStatic ?
				new JStaticDispatcher(this.advised.getTargetSource().getTarget()) : new JSerializableNoOp();

		Callback[] mainCallbacks = new Callback[] {
				aopInterceptor,  // for normal advice
				targetInterceptor,  // invoke target without considering advice, if optimized
				new JSerializableNoOp(),  // no override for methods mapped to this
				targetDispatcher, this.advisedDispatcher,
				new JEqualsInterceptor(this.advised),
				new JHashCodeInterceptor(this.advised)
		};

		Callback[] callbacks;

		// If the target is a static one and the advice chain is frozen,
		// then we can make some optimisations by sending the AOP calls
		// direct to the target using the fixed chain for that method.
		if (isStatic && isFrozen) {
			Method[] methods = rootClass.getMethods();
			Callback[] fixedCallbacks = new Callback[methods.length];
			this.fixedInterceptorMap = new HashMap<String, Integer>(methods.length);

			// TODO: small memory optimisation here (can skip creation for methods with no advice)
			for (int x = 0; x < methods.length; x++) {
				List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(methods[x], rootClass);
				fixedCallbacks[x] = new JFixedChainStaticTargetInterceptor(
						chain, this.advised.getTargetSource().getTarget(), this.advised.getTargetClass());
				this.fixedInterceptorMap.put(methods[x].toString(), x);
			}

			// Now copy both the callbacks from mainCallbacks
			// and fixedCallbacks into the callbacks array.
			callbacks = new Callback[mainCallbacks.length + fixedCallbacks.length];
			System.arraycopy(mainCallbacks, 0, callbacks, 0, mainCallbacks.length);
			System.arraycopy(fixedCallbacks, 0, callbacks, mainCallbacks.length, fixedCallbacks.length);
			this.fixedInterceptorOffset = mainCallbacks.length;
		}
		else {
			callbacks = mainCallbacks;
		}
		return callbacks;
	}
	
	/**
	 * Method interceptor used for static targets with no advice chain, when the
	 * proxy is to be exposed.
	 */
	private static class JStaticUnadvisedExposedInterceptor implements MethodInterceptor, Serializable {

		private final Object target;

		public JStaticUnadvisedExposedInterceptor(Object target) {
			this.target = target;
		}

		@Override
		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
			Object oldProxy = null;
			try {
				oldProxy = JAopContext.setCurrentProxy(proxy);
				Object retVal = methodProxy.invoke(this.target, args);
				return processReturnType(proxy, this.target, method, retVal);
			}
			finally {
				JAopContext.setCurrentProxy(oldProxy);
			}
		}
	}
	
	/**
	 * Interceptor for unadvised dynamic targets when the proxy needs exposing.
	 */
	private static class JDynamicUnadvisedExposedInterceptor implements MethodInterceptor, Serializable {

		private final JTargetSource targetSource;

		public JDynamicUnadvisedExposedInterceptor(JTargetSource targetSource) {
			this.targetSource = targetSource;
		}

		@Override
		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
			Object oldProxy = null;
			Object target = this.targetSource.getTarget();
			try {
				oldProxy = JAopContext.setCurrentProxy(proxy);
				Object retVal = methodProxy.invoke(target, args);
				return processReturnType(proxy, target, method, retVal);
			}
			finally {
				JAopContext.setCurrentProxy(oldProxy);
				this.targetSource.releaseTarget(target);
			}
		}
	}
	
	
	/**
	 * Method interceptor used for static targets with no advice chain. The call
	 * is passed directly back to the target. Used when the proxy needs to be
	 * exposed and it can't be determined that the method won't return
	 * {@code this}.
	 */
	private static class JStaticUnadvisedInterceptor implements MethodInterceptor, Serializable {

		private final Object target;

		public JStaticUnadvisedInterceptor(Object target) {
			this.target = target;
		}

		@Override
		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
			Object retVal = methodProxy.invoke(this.target, args);
			return processReturnType(proxy, this.target, method, retVal);
		}
	}

	
	/**
	 * Interceptor used to invoke a dynamic target without creating a method
	 * invocation or evaluating an advice chain. (We know there was no advice
	 * for this method.)
	 */
	private static class JDynamicUnadvisedInterceptor implements MethodInterceptor, Serializable {

		private final JTargetSource targetSource;

		public JDynamicUnadvisedInterceptor(JTargetSource targetSource) {
			this.targetSource = targetSource;
		}

		@Override
		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
			Object target = this.targetSource.getTarget();
			try {
				Object retVal = methodProxy.invoke(target, args);
				return processReturnType(proxy, target, method, retVal);
			}
			finally {
				this.targetSource.releaseTarget(target);
			}
		}
	}
	
	/**
	 * Dispatcher for a static target. Dispatcher is much faster than
	 * interceptor. This will be used whenever it can be determined that a
	 * method definitely does not return "this"
	 */
	private static class JStaticDispatcher implements Dispatcher, Serializable {

		private Object target;

		public JStaticDispatcher(Object target) {
			this.target = target;
		}

		@Override
		public Object loadObject() {
			return this.target;
		}
	}
	
	/**
	 * Serializable replacement for CGLIB's NoOp interface.
	 * Public to allow use elsewhere in the framework.
	 */
	public static class JSerializableNoOp implements NoOp, Serializable {
	}
	
	/**
	 * Dispatcher for the {@code equals} method.
	 * Ensures that the method call is always handled by this class.
	 */
	private static class JEqualsInterceptor implements MethodInterceptor, Serializable {

		private final JAdvisedSupport advised;

		public JEqualsInterceptor(JAdvisedSupport advised) {
			this.advised = advised;
		}

		@Override
		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) {
			Object other = args[0];
			if (proxy == other) {
				return true;
			}
			if (other instanceof Factory) {
				Callback callback = ((Factory) other).getCallback(INVOKE_EQUALS);
				if (!(callback instanceof JEqualsInterceptor)) {
					return false;
				}
				JAdvisedSupport otherAdvised = ((JEqualsInterceptor) callback).advised;
				return JAopUtils.equalsInProxy(this.advised, otherAdvised);
			}
			else {
				return false;
			}
		}
	}
	
	/**
	 * Dispatcher for the {@code hashCode} method.
	 * Ensures that the method call is always handled by this class.
	 */
	private static class JHashCodeInterceptor implements MethodInterceptor, Serializable {

		private final JAdvisedSupport advised;

		public JHashCodeInterceptor(JAdvisedSupport advised) {
			this.advised = advised;
		}

		@Override
		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) {
			return JCglibAopProxy.class.hashCode() * 13 + this.advised.getTargetSource().hashCode();
		}
	}
	
	/**
	 * Interceptor used specifically for advised methods on a frozen, static proxy.
	 */
	private static class JFixedChainStaticTargetInterceptor implements MethodInterceptor, Serializable {

		private final List<Object> adviceChain;

		private final Object target;

		private final Class<?> targetClass;

		public JFixedChainStaticTargetInterceptor(List<Object> adviceChain, Object target, Class<?> targetClass) {
			this.adviceChain = adviceChain;
			this.target = target;
			this.targetClass = targetClass;
		}

		@Override
		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
			MethodInvocation invocation = new JCglibMethodInvocation(proxy, this.target, method, args,
					this.targetClass, this.adviceChain, methodProxy);
			// If we get here, we need to create a MethodInvocation.
			Object retVal = invocation.proceed();
			retVal = processReturnType(proxy, this.target, method, retVal);
			return retVal;
		}
	}
	
	/**
	 * Dispatcher for any methods declared on the Advised class.
	 */
	private static class JAdvisedDispatcher implements Dispatcher, Serializable {

		private final JAdvisedSupport advised;

		public JAdvisedDispatcher(JAdvisedSupport advised) {
			this.advised = advised;
		}

		@Override
		public Object loadObject() throws Exception {
			return this.advised;
		}
	}
	
	
	/**
	 * General purpose AOP callback. Used when the target is dynamic or when the
	 * proxy is not frozen.
	 */
	private static class JDynamicAdvisedInterceptor implements MethodInterceptor, Serializable {

		private final JAdvisedSupport advised;

		public JDynamicAdvisedInterceptor(JAdvisedSupport advised) {
			this.advised = advised;
		}

		@Override
		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
			Object oldProxy = null;
			boolean setProxyContext = false;
			Class<?> targetClass = null;
			Object target = null;
			try {
				if (this.advised.exposeProxy) {
					// Make invocation available if necessary.
					oldProxy = JAopContext.setCurrentProxy(proxy);
					setProxyContext = true;
				}
				// May be null. Get as late as possible to minimize the time we
				// "own" the target, in case it comes from a pool...
				target = getTarget();
				if (target != null) {
					targetClass = target.getClass();
				}
				List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
				Object retVal;
				// Check whether we only have one InvokerInterceptor: that is,
				// no real advice, but just reflective invocation of the target.
				if (chain.isEmpty() && Modifier.isPublic(method.getModifiers())) {
					// We can skip creating a MethodInvocation: just invoke the target directly.
					// Note that the final invoker must be an InvokerInterceptor, so we know
					// it does nothing but a reflective operation on the target, and no hot
					// swapping or fancy proxying.
					Object[] argsToUse = JAopUtils.adaptArgumentsIfNecessary(method, args);
					retVal = methodProxy.invoke(target, argsToUse);
				}
				else {
					// We need to create a method invocation...
					retVal = new JCglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();
				}
				retVal = processReturnType(proxy, target, method, retVal);
				return retVal;
			}
			finally {
				if (target != null) {
					releaseTarget(target);
				}
				if (setProxyContext) {
					// Restore old proxy.
					JAopContext.setCurrentProxy(oldProxy);
				}
			}
		}

		@Override
		public boolean equals(Object other) {
			return (this == other ||
					(other instanceof JDynamicAdvisedInterceptor &&
							this.advised.equals(((JDynamicAdvisedInterceptor) other).advised)));
		}

		/**
		 * CGLIB uses this to drive proxy creation.
		 */
		@Override
		public int hashCode() {
			return this.advised.hashCode();
		}

		protected Object getTarget() throws Exception {
			return this.advised.getTargetSource().getTarget();
		}

		protected void releaseTarget(Object target) throws Exception {
			this.advised.getTargetSource().releaseTarget(target);
		}
	}
	
	
	/**
	 * CallbackFilter to assign Callbacks to methods.
	 */
	private static class ProxyCallbackFilter implements CallbackFilter {

		private final JAdvisedSupport advised;

		private final Map<String, Integer> fixedInterceptorMap;

		private final int fixedInterceptorOffset;

		public ProxyCallbackFilter(JAdvisedSupport advised, Map<String, Integer> fixedInterceptorMap, int fixedInterceptorOffset) {
			this.advised = advised;
			this.fixedInterceptorMap = fixedInterceptorMap;
			this.fixedInterceptorOffset = fixedInterceptorOffset;
		}

		/**
		 * Implementation of CallbackFilter.accept() to return the index of the
		 * callback we need.
		 * <p>The callbacks for each proxy are built up of a set of fixed callbacks
		 * for general use and then a set of callbacks that are specific to a method
		 * for use on static targets with a fixed advice chain.
		 * <p>The callback used is determined thus:
		 * <dl>
		 * <dt>For exposed proxies</dt>
		 * <dd>Exposing the proxy requires code to execute before and after the
		 * method/chain invocation. This means we must use
		 * DynamicAdvisedInterceptor, since all other interceptors can avoid the
		 * need for a try/catch block</dd>
		 * <dt>For Object.finalize():</dt>
		 * <dd>No override for this method is used.</dd>
		 * <dt>For equals():</dt>
		 * <dd>The EqualsInterceptor is used to redirect equals() calls to a
		 * special handler to this proxy.</dd>
		 * <dt>For methods on the Advised class:</dt>
		 * <dd>the AdvisedDispatcher is used to dispatch the call directly to
		 * the target</dd>
		 * <dt>For advised methods:</dt>
		 * <dd>If the target is static and the advice chain is frozen then a
		 * FixedChainStaticTargetInterceptor specific to the method is used to
		 * invoke the advice chain. Otherwise a DyanmicAdvisedInterceptor is
		 * used.</dd>
		 * <dt>For non-advised methods:</dt>
		 * <dd>Where it can be determined that the method will not return {@code this}
		 * or when {@code ProxyFactory.getExposeProxy()} returns {@code false},
		 * then a Dispatcher is used. For static targets, the StaticDispatcher is used;
		 * and for dynamic targets, a DynamicUnadvisedInterceptor is used.
		 * If it possible for the method to return {@code this} then a
		 * StaticUnadvisedInterceptor is used for static targets - the
		 * DynamicUnadvisedInterceptor already considers this.</dd>
		 * </dl>
		 */
		@Override
		public int accept(Method method) {
			if (JAopUtils.isFinalizeMethod(method)) {
				logger.debug("Found finalize() method - using NO_OVERRIDE");
				return NO_OVERRIDE;
			}
//			if (!this.advised.isOpaque() && method.getDeclaringClass().isInterface() &&
//					method.getDeclaringClass().isAssignableFrom(Advised.class)) {
//				if (logger.isDebugEnabled()) {
//					logger.debug("Method is declared on Advised interface: " + method);
//				}
//				return DISPATCH_ADVISED;
//			}
			// We must always proxy equals, to direct calls to this.
			if (JAopUtils.isEqualsMethod(method)) {
				logger.debug("Found 'equals' method: " + method);
				return INVOKE_EQUALS;
			}
			// We must always calculate hashCode based on the proxy.
			if (JAopUtils.isHashCodeMethod(method)) {
				logger.debug("Found 'hashCode' method: " + method);
				return INVOKE_HASHCODE;
			}
			Class<?> targetClass = this.advised.getTargetClass();
			// Proxy is not yet available, but that shouldn't matter.
			List<?> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
			boolean haveAdvice = !chain.isEmpty();
			boolean exposeProxy = this.advised.isExposeProxy();
			boolean isStatic = this.advised.getTargetSource().isStatic();
			boolean isFrozen = this.advised.isFrozen();
			if (haveAdvice || !isFrozen) {
				// If exposing the proxy, then AOP_PROXY must be used.
				if (exposeProxy) {
					if (logger.isDebugEnabled()) {
						logger.debug("Must expose proxy on advised method: " + method);
					}
					return AOP_PROXY;
				}
				String key = method.toString();
				// Check to see if we have fixed interceptor to serve this method.
				// Else use the AOP_PROXY.
				if (isStatic && isFrozen && this.fixedInterceptorMap.containsKey(key)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Method has advice and optimisations are enabled: " + method);
					}
					// We know that we are optimising so we can use the FixedStaticChainInterceptors.
					int index = this.fixedInterceptorMap.get(key);
					return (index + this.fixedInterceptorOffset);
				}
				else {
					if (logger.isDebugEnabled()) {
						logger.debug("Unable to apply any optimisations to advised method: " + method);
					}
					return AOP_PROXY;
				}
			}
			else {
				// See if the return type of the method is outside the class hierarchy
				// of the target type. If so we know it never needs to have return type
				// massage and can use a dispatcher.
				// If the proxy is being exposed, then must use the interceptor the
				// correct one is already configured. If the target is not static, then
				// cannot use a dispatcher because the target cannot be released.
				if (exposeProxy || !isStatic) {
					return INVOKE_TARGET;
				}
				Class<?> returnType = method.getReturnType();
				if (targetClass == returnType) {
					if (logger.isDebugEnabled()) {
						logger.debug("Method " + method +
								"has return type same as target type (may return this) - using INVOKE_TARGET");
					}
					return INVOKE_TARGET;
				}
				else if (returnType.isPrimitive() || !returnType.isAssignableFrom(targetClass)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Method " + method +
								" has return type that ensures this cannot be returned- using DISPATCH_TARGET");
					}
					return DISPATCH_TARGET;
				}
				else {
					if (logger.isDebugEnabled()) {
						logger.debug("Method " + method +
								"has return type that is assignable from the target type (may return this) - " +
								"using INVOKE_TARGET");
					}
					return INVOKE_TARGET;
				}
			}
		}

		@Override
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof ProxyCallbackFilter)) {
				return false;
			}
			ProxyCallbackFilter otherCallbackFilter = (ProxyCallbackFilter) other;
			JAdvisedSupport otherAdvised = otherCallbackFilter.advised;
			if (this.advised == null || otherAdvised == null) {
				return false;
			}
			if (this.advised.isFrozen() != otherAdvised.isFrozen()) {
				return false;
			}
			if (this.advised.isExposeProxy() != otherAdvised.isExposeProxy()) {
				return false;
			}
			if (this.advised.getTargetSource().isStatic() != otherAdvised.getTargetSource().isStatic()) {
				return false;
			}
			if (!JAopUtils.equalsProxiedInterfaces(this.advised, otherAdvised)) {
				return false;
			}
			// Advice instance identity is unimportant to the proxy class:
			// All that matters is type and ordering.
//			Advisor[] thisAdvisors = this.advised.getAdvisors();
//			Advisor[] thatAdvisors = otherAdvised.getAdvisors();
//			if (thisAdvisors.length != thatAdvisors.length) {
//				return false;
//			}
//			for (int i = 0; i < thisAdvisors.length; i++) {
//				Advisor thisAdvisor = thisAdvisors[i];
//				Advisor thatAdvisor = thatAdvisors[i];
//				if (!equalsAdviceClasses(thisAdvisor, thatAdvisor)) {
//					return false;
//				}
//				if (!equalsPointcuts(thisAdvisor, thatAdvisor)) {
//					return false;
//				}
//			}
			return true;
		}

//		private boolean equalsAdviceClasses(Advisor a, Advisor b) {
//			Advice aa = a.getAdvice();
//			Advice ba = b.getAdvice();
//			if (aa == null || ba == null) {
//				return (aa == ba);
//			}
//			return (aa.getClass() == ba.getClass());
//		}
//
//		private boolean equalsPointcuts(Advisor a, Advisor b) {
//			// If only one of the advisor (but not both) is PointcutAdvisor, then it is a mismatch.
//			// Takes care of the situations where an IntroductionAdvisor is used (see SPR-3959).
//			return (!(a instanceof PointcutAdvisor) ||
//					(b instanceof PointcutAdvisor &&
//							ObjectUtils.nullSafeEquals(((PointcutAdvisor) a).getPointcut(), ((PointcutAdvisor) b).getPointcut())));
//		}

		@Override
		public int hashCode() {
			int hashCode = 0;
//			Advisor[] advisors = this.advised.getAdvisors();
//			for (Advisor advisor : advisors) {
//				Advice advice = advisor.getAdvice();
//				if (advice != null) {
//					hashCode = 13 * hashCode + advice.getClass().hashCode();
//				}
//			}
			hashCode = 13 * hashCode + (this.advised.isFrozen() ? 1 : 0);
			hashCode = 13 * hashCode + (this.advised.isExposeProxy() ? 1 : 0);
//			hashCode = 13 * hashCode + (this.advised.isOptimize() ? 1 : 0);
//			hashCode = 13 * hashCode + (this.advised.isOpaque() ? 1 : 0);
			return hashCode;
		}
	}
	
	/**
	 * Process a return value. Wraps a return of {@code this} if necessary to be the
	 * {@code proxy} and also verifies that {@code null} is not returned as a primitive.
	 */
	private static Object processReturnType(Object proxy, Object target, Method method, Object retVal) {
		// Massage return value if necessary
		if (retVal != null && retVal == target 
//				&& !RawTargetAccess.class.isAssignableFrom(method.getDeclaringClass())
				) {
			// Special case: it returned "this". Note that we can't help
			// if the target sets a reference to itself in another returned object.
			retVal = proxy;
		}
		Class<?> returnType = method.getReturnType();
		if (retVal == null && returnType != Void.TYPE && returnType.isPrimitive()) {
			throw new JAopInvocationException(
					"Null return value from advice does not match primitive return type for: " + method);
		}
		return retVal;
	}
	
	
	/**
	 * Implementation of AOP Alliance MethodInvocation used by this AOP proxy.
	 */
	private static class JCglibMethodInvocation extends JReflectiveMethodInvocation {

		private final MethodProxy methodProxy;

		private final boolean publicMethod;

		public JCglibMethodInvocation(Object proxy, Object target, Method method, Object[] arguments,
				Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers, MethodProxy methodProxy) {

			super(proxy, target, method, arguments, targetClass, interceptorsAndDynamicMethodMatchers);
			this.methodProxy = methodProxy;
			this.publicMethod = Modifier.isPublic(method.getModifiers());
		}

		/**
		 * Gives a marginal performance improvement versus using reflection to
		 * invoke the target when invoking public methods.
		 */
		@Override
		protected Object invokeJoinpoint() throws Throwable {
			if (this.publicMethod) {
				return this.methodProxy.invoke(this.target, this.arguments);
			}
			else {
				return super.invokeJoinpoint();
			}
		}
	}
	
	/**
	 * Creates the CGLIB {@link Enhancer}. Subclasses may wish to override this to return a custom
	 * {@link Enhancer} implementation.
	 */
	protected Enhancer createEnhancer() {
		return new Enhancer();
	}
}
