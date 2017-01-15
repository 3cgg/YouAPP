package me.bunny.kernel.eventdriven.servicehub.aop;

import org.aopalliance.intercept.MethodInvocation;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

public class JServiceLoggerInterceptor
extends JServiceFactorySupport<JServiceLoggerInterceptor>
implements JServiceMethodInterceptorFactory<JServiceLoggerInterceptor
,JServiceLoggerInterceptor>,
JServiceMethodInterceptor
{

	protected final JLogger logger=JLoggerFactory.getLogger(getClass());
	
	public boolean accept(Class<?> targetClass) {
		return true;
	}
	
	public JServiceLoggerInterceptor() {
	}

	@Override
	protected JServiceLoggerInterceptor doGetService() {
		return this;
	}
	
	@Override
	public JServiceLoggerInterceptor getInterceptor() {
		return this;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		logger.debug("begin doing ... "+invocation.getMethod().getName());
		Object reVal= invocation.proceed();
		logger.debug("end up doing ... "+invocation.getMethod().getName());
		return reVal;
	}
	
	
	
}
