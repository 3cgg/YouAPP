package j.jave.kernal.jave.aop;

import j.jave.kernal.jave.utils.JAssert;

public class JSimpleAspectInstanceFactory implements JAspectInstanceFactory {

	private final Class<?> aspectClass;


	/**
	 * Create a new SimpleAspectInstanceFactory for the given aspect class.
	 * @param aspectClass the aspect class
	 */
	public JSimpleAspectInstanceFactory(Class<?> aspectClass) {
		JAssert.notNull(aspectClass, "Aspect class must not be null");
		this.aspectClass = aspectClass;
	}

	/**
	 * Return the specified aspect class (never {@code null}).
	 */
	public final Class<?> getAspectClass() {
		return this.aspectClass;
	}


	@Override
	public final Object getAspectInstance() {
		try {
			return this.aspectClass.newInstance();
		}
		catch (InstantiationException ex) {
			throw new JAopConfigException("Unable to instantiate aspect class [" + this.aspectClass.getName() + "]", ex);
		}
		catch (IllegalAccessException ex) {
			throw new JAopConfigException("Cannot access element class [" + this.aspectClass.getName() + "]", ex);
		}
	}

	@Override
	public ClassLoader getAspectClassLoader() {
		return this.aspectClass.getClassLoader();
	}

}