package test.j.jave.kernal.aop.jnterface;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TestLoggerInterceptor implements MethodInterceptor {

	private JLogger logger=JLoggerFactory.getLogger(getClass());
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		logger.info("logging interceptor...");
		
		return invocation.proceed();
	}

}
