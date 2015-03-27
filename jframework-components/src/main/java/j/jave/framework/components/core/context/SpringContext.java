/**
 * 
 */
package j.jave.framework.components.core.context;

import org.springframework.context.ApplicationContext;

/**
 * @author Administrator
 *
 */
public class SpringContext {

	private static ApplicationContext applicationContext;
	
	private static SpringContext springContext=null;
	
	static{
		springContext=new SpringContext();
	}

	private SpringContext(){ }
	
	public static final SpringContext get(){
		return springContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		SpringContext.applicationContext = applicationContext;
	}
	
	
	
}
