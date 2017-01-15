package j.jave.kernal.sql;

import me.bunny.kernel._c.proxy.JAtomicResourceSession;
import me.bunny.kernel._c.proxy.JAtomicResourceSessionGetter;

public class JPureSQLExecutingSessionGetter implements
		JAtomicResourceSessionGetter {

	@Override
	public JAtomicResourceSession getSession() throws Exception {
		return JSQLExecutingSessionHolder.getSession();
	}

}
