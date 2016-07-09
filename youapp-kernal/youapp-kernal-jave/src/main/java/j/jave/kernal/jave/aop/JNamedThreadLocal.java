package j.jave.kernal.jave.aop;

import j.jave.kernal.jave.utils.JAssert;

public class JNamedThreadLocal<T> extends ThreadLocal<T> {

	private final String name;


	/**
	 * Create a new NamedThreadLocal with the given name.
	 * @param name a descriptive name for this ThreadLocal
	 */
	public JNamedThreadLocal(String name) {
		JAssert.isNotEmpty(name, "Name must not be empty");
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
