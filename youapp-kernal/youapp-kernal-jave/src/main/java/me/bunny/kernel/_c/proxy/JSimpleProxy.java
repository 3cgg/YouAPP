package me.bunny.kernel._c.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class JSimpleProxy implements InvocationHandler, MethodInterceptor {
	
	public static final JLogger S_LOGGER =JLoggerFactory.getLogger(JSimpleProxy.class); 
	
	private Object enventSource;
	
	private Class<?> serviceClass;
	
	private Callback callbackObj;
	
	public static interface Callback{
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable;
		
		public Object intercept(Object obj, Method method, Object[] args,
				MethodProxy proxy) throws Throwable;
	}
	
	private static class IgnoreCallback implements Callback{
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			return null;
		}

		@Override
		public Object intercept(Object obj, Method method, Object[] args,
				MethodProxy proxy) throws Throwable {
			return null;
		}
	}
	
	private static final IgnoreCallback IGNORE_CALLBACK=new IgnoreCallback();
	
	/**
	 * create a proxy object for the specified class.
	 * @param enventSource which object the proxy derive from 
	 * @param clazz the proxy class
	 * @param callback what to do when the method is invoked in the proxy.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T proxy(Object enventSource, Class<T> clazz,Callback... callback){
		
		S_LOGGER.info("initialize proxy for , interface : "+clazz.getName());
		try{
			T proxyT=null;
			JSimpleProxy proxy=new JSimpleProxy();
			proxy.enventSource=enventSource;
			proxy.serviceClass=clazz;
			if(callback.length>0){
				proxy.callbackObj=callback[0];
			}
			else{
				proxy.callbackObj=IGNORE_CALLBACK;
			}
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
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return callbackObj.invoke(proxy, method, args);
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		return callbackObj.intercept(obj, method, args, proxy);
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}
	
	public Object getEnventSource() {
		return enventSource;
	}
	
}
