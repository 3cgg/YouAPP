package j.jave.kernal.jave.proxy;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JProxy implements InvocationHandler {
	
	public static final JLogger logger =JLoggerFactory.getLogger(JProxy.class); 
	
	protected JLogger targetLogger;

	private Object target;

	@SuppressWarnings("unchecked")
	public static <T> T proxy(Object obj, Class<T> clazz){
		
		logger.debug("initialize proxy for  instance object : "+obj.getClass()+" , interface : "+clazz.getName());
		try{
			JProxy proxy=new JProxy();
			proxy.target=(T)obj;
			proxy.targetLogger=JLoggerFactory.getLogger(obj.getClass().getName()); 
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
		try{
			return method.invoke(target, args);
		}catch (Exception e) {
			targetLogger.error("invoke proxy exception : ", e);
			throw e;
		}
	}

	
}
