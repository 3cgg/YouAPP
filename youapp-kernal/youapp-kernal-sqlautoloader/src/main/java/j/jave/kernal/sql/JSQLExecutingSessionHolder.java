package j.jave.kernal.sql;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;

public class JSQLExecutingSessionHolder {
	
	private static ThreadLocal<JSQLExecutingSession> threadLocal=new ThreadLocal<JSQLExecutingSession>();
	
	private static final JLogger LOGGER =JLoggerFactory.getLogger(JSQLExecutingSessionHolder.class);
	
	public static JSQLExecutingSession getSession(){
		return threadLocal.get();
	}
	
	public static void setSession(JSQLExecutingSession session){
		threadLocal.set(session);
	}
	
	public static void release(){
		threadLocal.remove();
	}
	
}
