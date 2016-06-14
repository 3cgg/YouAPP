package j.jave.kernal.jave.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * those implementers of {@link JServiceInterfaceProvider} should be modified by the annotation.
 * @author JIAZJ
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JServiceInterfaceShared{

}
