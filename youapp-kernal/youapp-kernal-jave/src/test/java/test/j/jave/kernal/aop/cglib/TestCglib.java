package test.j.jave.kernal.aop.cglib;

import j.jave.kernal.jave.aop.JAdvisedSupport;
import j.jave.kernal.jave.aop.JAspectJAroundAdvice;
import j.jave.kernal.jave.aop.JCglibAopProxy;
import j.jave.kernal.jave.aop.JSimpleAspectInstanceFactory;
import j.jave.kernal.jave.aop.JSingletonTargetSource;
import j.jave.kernal.jave.aop.JTargetSource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;

import test.j.jave.kernal.eventdriven.TestEventSupport;

public class TestCglib extends TestEventSupport {

	
	@Test
	public void testCglib(){
		try{
			JSimpleAspectInstanceFactory simpleAspectInstanceFactory
			=new JSimpleAspectInstanceFactory(TestLogger4CglibInterceptor.class);
			
			Method aspectJMethod=TestLogger4CglibInterceptor.class.getMethod("invoke", MethodInvocation.class);
			
			JAspectJAroundAdvice aroundAdvice=new JAspectJAroundAdvice(aspectJMethod, simpleAspectInstanceFactory);
			aroundAdvice.setAspectName("cglib-logger");
			aroundAdvice.setDeclarationOrder(999);
			List<Object> interceptors=new ArrayList<Object>();
			interceptors.add(aroundAdvice);
			JAdvisedSupport advisedSupport=new JAdvisedSupport();
			advisedSupport.setInterceptors(interceptors);
			
			JTargetSource targetSource=new JSingletonTargetSource(new TestLogger4CglibImpl());
			advisedSupport.setTargetSource(targetSource);
			
			JCglibAopProxy cglibAopProxy=new JCglibAopProxy(advisedSupport);
			
			
			Object object= cglibAopProxy.getProxy();
			
			TestLogger4CglibImpl.class.getDeclaredMethod("equals", Object.class)
			.invoke(object, "cglib tester.");
			
			TestLogger4CglibImpl.class.getDeclaredMethod("hashCode")
			.invoke(object);
			
			TestLogger4CglibImpl.class.getDeclaredMethod("show", String.class)
			.invoke(object, "cglib static -tester.");
			
			
			System.out.println("end-----");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
