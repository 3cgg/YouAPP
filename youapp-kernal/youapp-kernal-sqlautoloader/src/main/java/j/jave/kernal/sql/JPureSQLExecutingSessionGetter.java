package j.jave.kernal.sql;

import j.jave.kernal.jave.proxy.JAtomicResourceSession;
import j.jave.kernal.jave.proxy.JAtomicResourceSessionGetter;

public class JPureSQLExecutingSessionGetter implements
		JAtomicResourceSessionGetter {

	@Override
	public JAtomicResourceSession getSession() throws Exception {
		return JSQLExecutingSessionHolder.getSession();
	}

}
