package j.jave.kernal.sql;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

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
