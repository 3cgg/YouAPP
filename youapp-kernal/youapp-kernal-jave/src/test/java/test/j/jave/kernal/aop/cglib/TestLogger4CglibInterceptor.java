package test.j.jave.kernal.aop.cglib;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;

public class TestLogger4CglibInterceptor implements MethodInterceptor {

	private JLogger logger=JLoggerFactory.getLogger(getClass());
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		logger.info("logging interceptor...");
		
		return invocation.proceed();
	}

}
