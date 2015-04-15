/**
 * 
 */
package j.jave.framework.memcached;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Administrator
 *
 */
public class JMemcachedAspectJ {
	
	@Pointcut("execution(public * j.jave.framework.memcached.JMemcached.set(..))"
					+ "||execution(public * j.jave.framework.memcached.JMemcached.add(..))"
					+ "||execution(public * j.jave.framework.memcached.JMemcached.delete(..))")
	public void onInto(){}
	
	@Pointcut("execution(public * j.jave.framework.memcached.JMemcached.get(..))")
	public void onOut(){}
	
	
	@Around("onInto()")
	public void into(ProceedingJoinPoint pjp) throws Throwable{
		
		
		
	}
	
	@Around("onOut()")
	public void out(ProceedingJoinPoint pjp) throws Throwable{
		
		
	}
	
}
