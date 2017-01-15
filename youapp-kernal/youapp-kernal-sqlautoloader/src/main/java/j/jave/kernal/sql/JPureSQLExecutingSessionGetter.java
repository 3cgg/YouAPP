package j.jave.kernal.sql;

import me.bunny.kernel.jave.proxy.JAtomicResourceSession;
import me.bunny.kernel.jave.proxy.JAtomicResourceSessionGetter;

public class JPureSQLExecutingSessionGetter implements
		JAtomicResourceSessionGetter {

	@Override
	public JAtomicResourceSession getSession() throws Exception {
		return JSQLExecutingSessionHolder.getSession();
	}

}
