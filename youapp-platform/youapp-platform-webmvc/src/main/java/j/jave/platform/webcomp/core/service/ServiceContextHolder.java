package j.jave.platform.webcomp.core.service;

public final class ServiceContextHolder {
	
	private static final ThreadLocal<ServiceContext> THREAD_LOCAL=new ThreadLocal<ServiceContext>();
	
	public static final ServiceContext get(){
		return THREAD_LOCAL.get();
	}
	
	
}
