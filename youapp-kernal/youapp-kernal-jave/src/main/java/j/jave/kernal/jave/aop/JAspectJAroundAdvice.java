package j.jave.kernal.jave.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class JAspectJAroundAdvice extends JAbstractAspectJAdvice implements MethodInterceptor {

	public JAspectJAroundAdvice(Method aspectJAdviceMethod,
			JAspectInstanceFactory aspectInstanceFactory) {
		super(aspectJAdviceMethod, aspectInstanceFactory);
	}

	@Override
	public boolean isBeforeAdvice() {
		return false;
	}

	@Override
	public boolean isAfterAdvice() {
		return false;
	}

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		
		JProxyMethodInvocation pmi = (JProxyMethodInvocation) mi;
		return invokeAdviceMethodWithGivenArgs(argBinding(pmi, null, null));
	}

}