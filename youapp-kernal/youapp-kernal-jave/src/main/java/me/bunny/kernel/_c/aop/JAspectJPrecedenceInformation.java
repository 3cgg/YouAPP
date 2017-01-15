package me.bunny.kernel._c.aop;

public interface JAspectJPrecedenceInformation {

	// Implementation note:
		// We need the level of indirection this interface provides as otherwise the
		// AspectJPrecedenceComparator must ask an Advisor for its Advice in all cases
		// in order to sort advisors. This causes problems with the
		// InstantiationModelAwarePointcutAdvisor which needs to delay creating
		// its advice for aspects with non-singleton instantiation models.

		/**
		 * The name of the aspect (bean) in which the advice was declared.
		 */
		String getAspectName();

		/**
		 * The declaration order of the advice member within the aspect.
		 */
		int getDeclarationOrder();

		/**
		 * Return whether this is a before advice.
		 */
		boolean isBeforeAdvice();

		/**
		 * Return whether this is an after advice.
		 */
		boolean isAfterAdvice();

		
}
