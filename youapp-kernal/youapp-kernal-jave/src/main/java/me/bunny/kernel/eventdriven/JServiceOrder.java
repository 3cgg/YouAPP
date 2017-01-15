package me.bunny.kernel.eventdriven;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Documented
public @interface JServiceOrder {

	/**
	 * The order value.
	 * <p>Default is {@link JOrdered#LOWEST_PRECEDENCE}.
	 * @see JOrdered#getOrder()
	 */
	int value() default JOrdered.LOWEST_PRECEDENCE;

	/**
	 * which listener the service is ordered.
	 * @return
	 */
	Class<?>[] listenerClasses() default {};
}
