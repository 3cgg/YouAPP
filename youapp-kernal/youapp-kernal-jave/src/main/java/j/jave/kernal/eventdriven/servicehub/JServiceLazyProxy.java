package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

class JServiceLazyProxy implements InvocationHandler, MethodInterceptor {
	
	public static final JLogger logger =JLoggerFactory.getLogger(JServiceLazyProxy.class); 
	
	private JLogger LOGGER;

	private Object target;
	
	private Object enventSource;
	
	private Class<?> serviceClass;
	
	@SuppressWarnings("unchecked")
	public static <T> T proxy(Object enventSource, Class<T> clazz){
		
		logger.debug("initialize proxy for , interface : "+clazz.getName());
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
			logger.error("initialized proxy fail : ",e);
			throw new RuntimeException(e);
		}
	}
	
	private final Lock lock=new ReentrantLock();
	
	private void logging(String message){
		if(LOGGER!=null){
			LOGGER.debug(message);
		}
		else{
			System.out.println(message);
		}
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		try{
			logging("--------------------------------invoke method (JDK) : ("+method.getName()+")...........................");
			if(target==null){
				try{
					lock.lock();
					if(target==null){
						logging(" getting service from hub (JDK) : "+serviceClass.getName());
						LOGGER=JLoggerFactory.getLogger(serviceClass.getName());
						target=JServiceHubDelegate.get().getActualService(enventSource, serviceClass);
						logging(" had get service from hub (JDK) : "+String.valueOf(target));
					}
				}finally{
					lock.unlock();
				}
			}
			return method.invoke(target, args);
		}catch (Exception e) {
			LOGGER.error("invoke proxy exception : ", e);
			throw e.getCause();
		}
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		try{
			logging("--------------------------------invoke method (cglib): ("+method.getName()+")...........................");
			if(target==null){
				try{
					lock.lock();
					if(target==null){
						logging(" getting service from hub (cglib) : "+serviceClass.getName());
						LOGGER=JLoggerFactory.getLogger(serviceClass.getName());
						target=JServiceHubDelegate.get().getActualService(enventSource, serviceClass);
						logging(" had get service from hub (cglib) : "+String.valueOf(target));
					}
				}finally{
					lock.unlock();
				}
			}
			return proxy.invoke(target, args);
		}catch (Exception e) {
			LOGGER.error("invoke proxy exception : ", e);
			throw e;
		}
	}

	
}
