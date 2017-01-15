package me.bunny.kernel._c.aop;

import me.bunny.kernel._c.utils.JAssert;

public class JSingletonAspectInstanceFactory implements JAspectInstanceFactory {

	private final Object aspectInstance;


	/**
	 * Create a new SingletonAspectInstanceFactory for the given aspect instance.
	 * @param aspectInstance the singleton aspect instance
	 */
	public JSingletonAspectInstanceFactory(Object aspectInstance) {
		JAssert.notNull(aspectInstance, "Aspect instance must not be null");
		this.aspectInstance = aspectInstance;
	}


	@Override
	public final Object getAspectInstance() {
		return this.aspectInstance;
	}

	@Override
	public ClassLoader getAspectClassLoader() {
		return this.aspectInstance.getClass().getClassLoader();
	}

}