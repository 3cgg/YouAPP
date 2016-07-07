package j.jave.kernal.jave.proxy;

public class DefaultAtomicResourceSessionGetter implements
		JAtomicResourceSessionGetter {

	@Override
	public JAtomicResourceSession getSession() throws Exception {
		return JAtomicResourceSessionHolder.getAtomicResourceSession();
	}

}
