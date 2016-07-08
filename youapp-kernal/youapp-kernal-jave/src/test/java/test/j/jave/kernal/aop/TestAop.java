package test.j.jave.kernal.aop;

import j.jave.kernal.jave.aop.JAdvisedSupport;
import j.jave.kernal.jave.aop.JAspectJAroundAdvice;
import j.jave.kernal.jave.aop.JJdkDynamicAopProxy;
import j.jave.kernal.jave.aop.JSimpleAspectInstanceFactory;
import j.jave.kernal.jave.aop.JSingletonTargetSource;
import j.jave.kernal.jave.aop.JTargetSource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;

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
			advisedSupport.setInterfaces(classes);
			
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
