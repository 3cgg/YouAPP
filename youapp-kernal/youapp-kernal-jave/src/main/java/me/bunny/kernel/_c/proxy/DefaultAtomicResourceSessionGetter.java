package me.bunny.kernel._c.proxy;

public class DefaultAtomicResourceSessionGetter implements
		JAtomicResourceSessionGetter {

	@Override
	public JAtomicResourceSession getSession() throws Exception {
		return JAtomicResourceSessionHolder.getAtomicResourceSession();
	}

}
