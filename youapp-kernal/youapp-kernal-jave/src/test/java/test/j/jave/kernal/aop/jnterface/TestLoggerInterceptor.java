package test.j.jave.kernal.aop.jnterface;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;

public class TestLoggerInterceptor implements MethodInterceptor {

	private JLogger logger=JLoggerFactory.getLogger(getClass());
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		logger.info("logging interceptor...");
		
		return invocation.proceed();
	}

}
