package me.bunny.kernel.eventdriven.servicehub.aop;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactory;
import me.bunny.kernel.jave.service.JService;


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
