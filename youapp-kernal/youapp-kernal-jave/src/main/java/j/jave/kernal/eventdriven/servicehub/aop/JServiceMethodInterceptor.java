package j.jave.kernal.eventdriven.servicehub.aop;

import j.jave.kernal.eventdriven.servicehub.JServiceFactory;
import j.jave.kernal.jave.service.JService;

import org.aopalliance.intercept.MethodInterceptor;


public interface JServiceMethodInterceptor<T extends JServiceMethodInterceptor<T>> 
extends JService, MethodInterceptor,JServiceFactory<T>{

	T getInterceptor();
	
	/**
	 * @param targetClass
	 * @return
	 */
	boolean accept(Class<?> targetClass);
	
}
