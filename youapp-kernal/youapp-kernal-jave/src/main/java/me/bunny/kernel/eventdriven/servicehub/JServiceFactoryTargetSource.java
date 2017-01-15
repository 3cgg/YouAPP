package me.bunny.kernel.eventdriven.servicehub;

import me.bunny.kernel.jave.aop.JTargetSource;
import me.bunny.kernel.jave.utils.JAssert;
import me.bunny.kernel.jave.utils.JLangUtils;

public class JServiceFactoryTargetSource implements JTargetSource {

	private final JServiceFactorySupport<?> serviceFactory;


	/**
	 * Create a new SingletonTargetSource for the given service factory.
	 * @param target the service factory
	 */
	public JServiceFactoryTargetSource(JServiceFactorySupport<?> serviceFactory) {
		JAssert.notNull(serviceFactory, "Target service facotry must not be null");
		this.serviceFactory = serviceFactory;
	}


	@Override
	public Class<?> getTargetClass() {
		return serviceFactory.getServiceImplClass();
	}

	@Override
	public Object getTarget() {
		return this.serviceFactory.innerGetService();
	}

	@Override
	public void releaseTarget(Object target) {
		// nothing to do
	}

	@Override
	public boolean isStatic() {
		return true;
	}


	/**
	 * Two invoker interceptors are equal if they have the same target or if the
	 * targets or the targets are equal.
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof JServiceFactoryTargetSource)) {
			return false;
		}
		JServiceFactoryTargetSource otherTargetSource = (JServiceFactoryTargetSource) other;
		return this.serviceFactory.equals(otherTargetSource.serviceFactory);
	}

	/**
	 * SingletonTargetSource uses the hash code of the target object.
	 */
	@Override
	public int hashCode() {
		return this.serviceFactory.hashCode();
	}

	@Override
	public String toString() {
		return "SingletonTargetSource for target object [" + JLangUtils.identityToString(this.serviceFactory) + "]";
	}
	
	
}
