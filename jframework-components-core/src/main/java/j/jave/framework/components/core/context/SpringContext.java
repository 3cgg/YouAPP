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

	private ApplicationContext applicationContext;
	
	private static SpringContext springContext=null;
	
	static{
		springContext=new SpringContext();
	}

	private SpringContext(){ }
	
	public static final SpringContext get(){
		return springContext;
	}

	public  ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public  void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	
	
}
