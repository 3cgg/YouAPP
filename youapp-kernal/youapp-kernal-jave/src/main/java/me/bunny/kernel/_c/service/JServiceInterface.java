package me.bunny.kernel._c.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * the service modified by this annotation means the service is an individual servicer.
 * @author JIAZJ
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JServiceInterface{

}
