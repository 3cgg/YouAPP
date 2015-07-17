package j.jave.framework.commons.proxy;

import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * the proxy ,  wraps target service , can make the operation on the resource under the same session .
 * @author J
 */
public class JAtomicResourceProxy implements InvocationHandler {

	public static final JLogger logger =JLoggerFactory.getLogger(JAtomicResourceProxy.class);
	
	private Object target;
	
	@SuppressWarnings("unchecked")
	public static <T> T proxy(Object obj, Class<T> clazz){
		
		logger.debug("initialize proxy for  instance object : "+obj.getClass()+" , interface : "+clazz.getName());
		try{
			JAtomicResourceProxy proxy=new JAtomicResourceProxy();
			proxy.target=obj;
			return (T)java.lang.reflect.Proxy.newProxyInstance
				(clazz.getClassLoader(), new Class[]{ clazz }, proxy);
		}catch (Exception e) {
			logger.error("initialized proxy fail : ",e);
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		JAtomicResourceSession atomicResourceSession= JAtomicResourceSessionHolder.getAtomicResourceSession();
		Monitor.get().in();
		Throwable throwable=null; 
		Object object = null;
		try{
			object = method.invoke(target, args);
		}catch (Exception e) {
			logger.error(target.getClass().getName()+"(proxy) invoke encounter exception : ", e);
			throwable=e;
			throw e;
		}finally{
			if(throwable!=null){
				atomicResourceSession.rollback();
				Monitor.remove();
				JAtomicResourceSessionHolder.release();
			}
			
			if(Monitor.get().out()){
				atomicResourceSession.commit();
				Monitor.remove();
				JAtomicResourceSessionHolder.release();
			}
		}
		return object;
	}
	
	static class Monitor{
		
		private static ThreadLocal<Monitor> threadLocal=new ThreadLocal<Monitor>();
		
		private static final JLogger logger =JLoggerFactory.getLogger(Monitor.class);
		
		private volatile int count; 
		
		public boolean in(){
			++count;
			return true;
		}

		public boolean out(){
			return (--count)==0;
		}
		
		public static void remove(){
			threadLocal.remove();
		}
		public static Monitor get(){
			Monitor monitor= threadLocal.get();
			if(monitor==null){
				monitor=new Monitor();
				threadLocal.set(monitor);
				logger.debug("use provided sync lock , current thread :  "+Thread.currentThread().getName());
			}
			else{
				logger.debug("use tied sync lock , current thread :  "+Thread.currentThread().getName());
			}
			return monitor;
		}
		
	}
	
	
}
