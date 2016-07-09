package j.jave.kernal.eventdriven.servicehub.aop;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

import org.aopalliance.intercept.MethodInvocation;

public class JServiceLoggerInterceptor
extends JServiceFactorySupport<JServiceLoggerInterceptor>
implements JServiceMethodInterceptor<JServiceLoggerInterceptor>
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
