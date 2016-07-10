package j.jave.kernal.eventdriven.servicehub.aop;

import j.jave.kernal.eventdriven.servicehub.JServiceFactory;
import j.jave.kernal.jave.service.JService;


public interface JServiceMethodInterceptorFactory<T extends JServiceMethodInterceptor
,M extends JServiceMethodInterceptorFactory<T, M>> 
extends JServiceFactory<M> ,JService {

	T getInterceptor();
	
	/**
	 * @param targetClass
	 * @return
	 */
	boolean accept(Class<?> targetClass);
	
}
