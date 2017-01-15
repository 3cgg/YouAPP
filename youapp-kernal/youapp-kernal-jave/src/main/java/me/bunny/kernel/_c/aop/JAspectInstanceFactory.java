package me.bunny.kernel._c.aop;

public interface JAspectInstanceFactory {

	/**
	 * Create an instance of this factory's aspect.
	 * @return the aspect instance (never {@code null})
	 */
	Object getAspectInstance();

	/**
	 * Expose the aspect class loader that this factory uses.
	 * @return the aspect class loader (never {@code null})
	 */
	ClassLoader getAspectClassLoader();
	
}
