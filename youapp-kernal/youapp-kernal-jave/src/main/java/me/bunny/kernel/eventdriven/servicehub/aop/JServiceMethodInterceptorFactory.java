package me.bunny.kernel.eventdriven.servicehub.aop;

import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactory;


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
