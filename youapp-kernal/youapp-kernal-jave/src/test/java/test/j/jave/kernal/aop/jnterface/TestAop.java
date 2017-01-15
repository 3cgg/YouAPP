package test.j.jave.kernal.aop.jnterface;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;

import me.bunny.kernel._c.aop.JAdvisedSupport;
import me.bunny.kernel._c.aop.JAspectJAroundAdvice;
import me.bunny.kernel._c.aop.JJdkDynamicAopProxy;
import me.bunny.kernel._c.aop.JSimpleAspectInstanceFactory;
import me.bunny.kernel._c.aop.JSingletonTargetSource;
import me.bunny.kernel._c.aop.JTargetSource;
import test.j.jave.kernal.eventdriven.TestEventSupport;

public class TestAop extends TestEventSupport {

	
	@Test
	public void testAop(){
		try{
			JSimpleAspectInstanceFactory simpleAspectInstanceFactory
			=new JSimpleAspectInstanceFactory(TestLoggerInterceptor.class);
			
			Method aspectJMethod=TestLoggerInterceptor.class.getMethod("invoke", MethodInvocation.class);
			
			JAspectJAroundAdvice aroundAdvice=new JAspectJAroundAdvice(aspectJMethod, simpleAspectInstanceFactory);
			aroundAdvice.setAspectName("aop-logger");
			aroundAdvice.setDeclarationOrder(999);
			List<Object> interceptors=new ArrayList<Object>();
			interceptors.add(aroundAdvice);
			JAdvisedSupport advisedSupport=new JAdvisedSupport();
			advisedSupport.setInterceptors(interceptors);
			List<Class<?>> classes=new ArrayList<Class<?>>();
			classes.add(TestLoggerService.class);
			advisedSupport.setInterfaces(classes.toArray(new Class<?>[]{}));
			
			JTargetSource targetSource=new JSingletonTargetSource(new TestLoggerServiceImpl());
			advisedSupport.setTargetSource(targetSource);
			
			JJdkDynamicAopProxy jdkDynamicAopProxy=new JJdkDynamicAopProxy(advisedSupport);
			
			Object object= jdkDynamicAopProxy.getProxy();
			
			TestLoggerService.class.getDeclaredMethod("log", String.class)
			.invoke(object, "aop tester.");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
}
