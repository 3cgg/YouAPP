package me.bunny.kernel._c.aop;

import me.bunny.kernel._c.utils.JAssert;
import me.bunny.kernel._c.utils.JLangUtils;

public class JSingletonTargetSource implements JTargetSource {

	/** Target cached and invoked using reflection */
	private final Object target;


	/**
	 * Create a new SingletonTargetSource for the given target.
	 * @param target the target object
	 */
	public JSingletonTargetSource(Object target) {
		JAssert.notNull(target, "Target object must not be null");
		this.target = target;
	}


	@Override
	public Class<?> getTargetClass() {
		return this.target.getClass();
	}

	@Override
	public Object getTarget() {
		return this.target;
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
		if (!(other instanceof JSingletonTargetSource)) {
			return false;
		}
		JSingletonTargetSource otherTargetSource = (JSingletonTargetSource) other;
		return this.target.equals(otherTargetSource.target);
	}

	/**
	 * SingletonTargetSource uses the hash code of the target object.
	 */
	@Override
	public int hashCode() {
		return this.target.hashCode();
	}

	@Override
	public String toString() {
		return "SingletonTargetSource for target object [" + JLangUtils.identityToString(this.target) + "]";
	}
	
	
}
