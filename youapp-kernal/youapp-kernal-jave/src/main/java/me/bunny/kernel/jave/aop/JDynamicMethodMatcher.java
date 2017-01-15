package me.bunny.kernel.jave.aop;

import java.lang.reflect.Method;

public abstract class JDynamicMethodMatcher implements JMethodMatcher {

	@Override
	public final boolean isRuntime() {
		return true;
	}

	/**
	 * Can override to add preconditions for dynamic matching. This implementation
	 * always returns true.
	 */
	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		return true;
	}

}