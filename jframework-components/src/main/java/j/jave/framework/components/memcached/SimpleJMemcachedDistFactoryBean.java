/**
 * 
 */
package j.jave.framework.components.memcached;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author Administrator
 *
 */
public class SimpleJMemcachedDistFactoryBean extends MemcachedConfiguration implements FactoryBean<JMemcachedDist>  {
	
	private JMemcachedDist jMemcachedDist;
	
	private Object object=new Object();
	
	public SimpleJMemcachedDistFactoryBean(){
		System.out.println("SimpleJMemcachedDistFactoryBean");
	}
	
	


	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	public void postProcessAfter()
			throws BeansException {
		
		
	}




	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public JMemcachedDist getObject() throws Exception {
		if (jMemcachedDist == null)
			jMemcachedDist = new JMemcachedDist(storeAddes, backupAddes);
		return jMemcachedDist;
	}




	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return JMemcachedDistService.class;
	}




	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

	
	
	
	
	
	
	
	
}
