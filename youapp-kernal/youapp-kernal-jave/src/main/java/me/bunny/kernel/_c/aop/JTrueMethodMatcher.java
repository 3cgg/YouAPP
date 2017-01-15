package me.bunny.kernel._c.aop;

import java.lang.reflect.Method;

public class JTrueMethodMatcher implements JMethodMatcher {



	public static final JTrueMethodMatcher INSTANCE = new JTrueMethodMatcher();


	/**
	 * Enforce Singleton pattern.
	 */
	private JTrueMethodMatcher() {
	}


	@Override
	public boolean isRuntime() {
		return false;
	}

	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		return true;
	}

	@Override
	public boolean matches(Method method, Class<?> targetClass, Object... args) {
		// Should never be invoked as isRuntime returns false.
		throw new UnsupportedOperationException();
	}


	@Override
	public String toString() {
		return "MethodMatcher.TRUE";
	}

	/**
	 * Required to support serialization. Replaces with canonical
	 * instance on deserialization, protecting Singleton pattern.
	 * Alternative to overriding {@code equals()}.
	 */
	private Object readResolve() {
		return INSTANCE;
	}


	
}
