package j.jave.kernal.eventdriven;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * give an chance to select multiple {@link JServiceOrder}s
 * @author JIAZJ
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Documented
public @interface JServiceOrders {

	JServiceOrder[] serviceOrders() default {};

}
