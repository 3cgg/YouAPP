package j.jave.kernal.jave.proxy;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class JProxy implements InvocationHandler, MethodInterceptor {
	
	public static final JLogger logger =JLoggerFactory.getLogger(JProxy.class); 
	
	private JLogger targetLogger;

	private Object target;
	
	@SuppressWarnings("unchecked")
	public static <T> T proxy(Object obj, Class<T> clazz){
		
		logger.debug("initialize proxy for  instance object : "+obj.getClass()+" , interface : "+clazz.getName());
		try{
			T proxyT=null;
			JProxy proxy=new JProxy();
			proxy.target=(T)obj;
			proxy.targetLogger=JLoggerFactory.getLogger(obj.getClass().getName()); 
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
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		try{
			return method.invoke(target, args);
		}catch (Exception e) {
			targetLogger.error("invoke proxy exception : ", e);
			throw e;
		}
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		try{
			return proxy.invokeSuper(obj, args);
		}catch (Exception e) {
			targetLogger.error("invoke proxy exception : ", e);
			throw e;
		}
	}

	
}
