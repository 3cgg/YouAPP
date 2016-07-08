package j.jave.kernal.jave.aop;

import j.jave.kernal.jave.reflect.JReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class JAbstractAspectJAdvice implements JAdvice, JAspectJPrecedenceInformation {

	protected final Method aspectJAdviceMethod;
	
	private final JAspectInstanceFactory aspectInstanceFactory;
	
	/**
	 * The name of the aspect (ref bean) in which this advice was defined (used
	 * when determining advice precedence so that we can determine
	 * whether two pieces of advice come from the same aspect).
	 */
	private String aspectName;
	
	/** The total number of arguments we have to populate on advice dispatch */
	private final int adviceInvocationArgumentCount;
	
	/**
	 * The order of declaration of this advice within the aspect.
	 */
	private int declarationOrder;

	public JAbstractAspectJAdvice(Method aspectJAdviceMethod,
			JAspectInstanceFactory aspectInstanceFactory) {
		this.aspectJAdviceMethod = aspectJAdviceMethod;
		this.aspectInstanceFactory = aspectInstanceFactory;
		this.adviceInvocationArgumentCount = this.aspectJAdviceMethod.getParameterTypes().length;
	}

	public String getAspectName() {
		return aspectName;
	}

	public void setAspectName(String aspectName) {
		this.aspectName = aspectName;
	}

	public int getDeclarationOrder() {
		return declarationOrder;
	}

	public void setDeclarationOrder(int declarationOrder) {
		this.declarationOrder = declarationOrder;
	}
	
	/**
	 * Take the arguments at the method execution join point and output a set of arguments
	 * to the advice method
	 * @param returnValue the return value from the method execution (may be null)
	 * @param ex the exception thrown by the method execution (may be null)
	 * @return the empty array if there are no arguments
	 */
	protected Object[] argBinding(JProxyMethodInvocation methodInvocation, Object returnValue, Throwable ex) {

		// AMC start
		Object[] adviceInvocationArgs = new Object[this.adviceInvocationArgumentCount];
		adviceInvocationArgs[0]=methodInvocation;
		int numBound = 0;
//
//		if (this.joinPointArgumentIndex != -1) {
//			adviceInvocationArgs[this.joinPointArgumentIndex] = jp;
//			numBound++;
//		}
//		else if (this.joinPointStaticPartArgumentIndex != -1) {
//			adviceInvocationArgs[this.joinPointStaticPartArgumentIndex] = jp.getStaticPart();
//			numBound++;
//		}

//		if (!CollectionUtils.isEmpty(this.argumentBindings)) {
//			// binding from pointcut match
//			if (jpMatch != null) {
//				PointcutParameter[] parameterBindings = jpMatch.getParameterBindings();
//				for (PointcutParameter parameter : parameterBindings) {
//					String name = parameter.getName();
//					Integer index = this.argumentBindings.get(name);
//					adviceInvocationArgs[index] = parameter.getBinding();
//					numBound++;
//				}
//			}
//			// binding from returning clause
//			if (this.returningName != null) {
//				Integer index = this.argumentBindings.get(this.returningName);
//				adviceInvocationArgs[index] = returnValue;
//				numBound++;
//			}
//			// binding from thrown exception
//			if (this.throwingName != null) {
//				Integer index = this.argumentBindings.get(this.throwingName);
//				adviceInvocationArgs[index] = ex;
//				numBound++;
//			}
//		}
//
//		if (numBound != this.adviceInvocationArgumentCount) {
//			throw new IllegalStateException("Required to bind " + this.adviceInvocationArgumentCount +
//					" arguments, but only bound " + numBound + " (JoinPointMatch " +
//					(jpMatch == null ? "was NOT" : "WAS") + " bound in invocation)");
//		}
		
		
		
//		Class<?>[] paramTypes= aspectJAdviceMethod.getParameterTypes();
//		for(Class<?> clazz:paramTypes ){
//			if(MethodInvocation.class.isAssignableFrom(clazz)){
//				
//			}
//		}
		
//		for (Object parameter : methodInvocation.getArguments()) {
//			adviceInvocationArgs[index] = parameter.getBinding();
//			numBound++;
//		}
		
		return adviceInvocationArgs;
	}
	
	
	protected Object invokeAdviceMethodWithGivenArgs(Object[] args) throws Throwable {
		Object[] actualArgs = args;
		if (this.aspectJAdviceMethod.getParameterTypes().length == 0) {
			actualArgs = null;
		}
		try {
			JReflectionUtils.makeAccessible(this.aspectJAdviceMethod);
			// TODO AopUtils.invokeJoinpointUsingReflection
			return this.aspectJAdviceMethod.invoke(this.aspectInstanceFactory.getAspectInstance(), actualArgs);
		}
		catch (IllegalArgumentException ex) {
			throw new JAopInvocationException("Mismatch on arguments to advice method [" +
					this.aspectJAdviceMethod + "]; pointcut expression [" +
//					this.pointcut.getPointcutExpression() + 
					"]", ex);
		}
		catch (InvocationTargetException ex) {
			throw ex.getTargetException();
		}
	}
	
}
