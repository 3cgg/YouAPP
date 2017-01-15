package me.bunny.kernel.eventdriven.servicehub;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

class JServiceLazyProxy implements InvocationHandler, MethodInterceptor {
	
	public static final JLogger S_LOGGER =JLoggerFactory.getLogger(JServiceLazyProxy.class); 
	
	private JLogger logger;

	private Object target;
	
	private Object enventSource;
	
	private Class<?> serviceClass;
	
	@SuppressWarnings("unchecked")
	public static <T> T proxy(Object enventSource, Class<T> clazz){
		
		S_LOGGER.info("initialize proxy for , interface : "+clazz.getName());
		try{
			T proxyT=null;
			JServiceLazyProxy proxy=new JServiceLazyProxy();
			proxy.enventSource=enventSource;
			proxy.serviceClass=clazz;
			if(clazz.isInterface()){
				proxyT= (T)java.lang.reflect.Proxy.newProxyInstance
					(clazz.getClassLoader(), new Class[]{ clazz }, proxy);
			}
			else{
				Enhancer enhancer = new Enhancer();  
		        enhancer.setSuperclass(clazz);  
		        enhancer.setCallback(proxy);  
		        proxyT= (T) enhancer.create();  
			}
			return proxyT;
			
		}catch (Exception e) {
			S_LOGGER.error("initialized proxy fail : ",e);
			throw new RuntimeException(e);
		}
	}
	
	private JLogger getLogger(){
		return logger==null?S_LOGGER:logger;
	}
	
	private final Lock lock=new ReentrantLock();
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		try{
			if(S_LOGGER.isDebugEnabled()){
				getLogger().debug("--------------------------------invoke method (JDK) : ("+method.getName()+")...........................");
			}
			if(target==null){
				try{
					lock.lock();
					if(target==null){
						S_LOGGER.info(" getting service from hub (JDK) : "+serviceClass.getName());
						logger=JLoggerFactory.getLogger(serviceClass.getName());
						target=JServiceHubDelegate.get().getActualService(enventSource, serviceClass);
						S_LOGGER.info(" had get service from hub (JDK) : "+String.valueOf(target));
					}
				}finally{
					lock.unlock();
				}
			}
			return method.invoke(target, args);
		}catch (Exception e) {
			getLogger().error("invoke proxy exception : ", e);
			if(e.getCause()!=null){
				throw e.getCause();
			}
			throw e;
		}
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		try{
			if(S_LOGGER.isDebugEnabled()){
				getLogger().debug("--------------------------------invoke method (cglib): ("+method.getName()+")...........................");
			}
			if(target==null){
				try{
					lock.lock();
					if(target==null){
						S_LOGGER.info(" getting service from hub (cglib) : "+serviceClass.getName());
						logger=JLoggerFactory.getLogger(serviceClass.getName());
						target=JServiceHubDelegate.get().getActualService(enventSource, serviceClass);
						S_LOGGER.info(" had get service from hub (cglib) : "+String.valueOf(target));
					}
				}finally{
					lock.unlock();
				}
			}
			return proxy.invoke(target, args);
		}catch (Exception e) {
			getLogger().error("invoke proxy exception : ", e);
			if(e.getCause()!=null){
				throw e.getCause();
			}
			throw e;
		}
	}

	
}
