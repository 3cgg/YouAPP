package me.bunny.kernel.mock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JMock
public @interface JJSONMock {

	/**
	 * the data
	 * @return
	 */
	String data() default "" ;
	
	/**
	 * where the data locates, its higher than {@link #data()}
	 * @return
	 * @see URI
	 */
	String uri() default "";
	
	/**
	 * whether to mock the target or not
	 * @return
	 */
	boolean mock() default true;
	
}
