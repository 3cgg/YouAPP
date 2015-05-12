/**
 * 
 */
package j.jave.framework.components.core.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Quickly Get Spring Context. the property {@link #applicationContext} can be initialized during the Spring Initialization.
 * <strong>note that the class can be only initialized by Spring , any new instance never supports the ability of exposing Spring Context</strong>
 * @author J
 * @see ApplicationContextAware
 */
@Service("SpringContextSupport-Get-Bean-Not-Supported")
public class SpringContextSupport implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public static  ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public  void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextSupport.applicationContext = applicationContext;
	}
	
}
