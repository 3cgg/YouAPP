/**
 * 
 */
package j.jave.framework.components.support.ehcache.subhub;

import j.jave.framework.commons.ehcache.JDefaultEhcacheService;
import j.jave.framework.commons.ehcache.JDefaultEhcacheServiceConfiguration;
import j.jave.framework.commons.ehcache.JEhcacheServiceAware;
import j.jave.framework.commons.ehcache.JEhcacheServiceConfigure;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;
import net.sf.ehcache.Ehcache;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="ehcacheServiceFactory")
public class EhcacheServiceFactory extends SpringServiceFactorySupport<EhcacheService> {
	
	public EhcacheServiceFactory() {
		super(EhcacheService.class);
	}
	
	@Autowired(required=false)
	private JEhcacheServiceConfigure ehcacheServiceConfigure;
	
	
	private EhcacheService ehcacheService;
	
	private Object sync=new Object();
	
	@Override
	public EhcacheService getService() {
		
		if(ehcacheService==null){
			synchronized (sync) {
				
				if(ehcacheServiceConfigure==null){
					ehcacheServiceConfigure=new JDefaultEhcacheServiceConfiguration();
				}
				
				// Ehcache may holded by spring. 
				if(BeanSupportEhcacheServiceConfigure.class.isInstance(ehcacheServiceConfigure)){
					BeanSupportEhcacheServiceConfigure springHolder=(BeanSupportEhcacheServiceConfigure) ehcacheServiceConfigure;
					String beanName=springHolder.getEhcacheBeanName();
					if(JStringUtils.isNotNullOrEmpty(beanName)){
						Object bean=getBeanByName(beanName,Object.class);
						if(FactoryBean.class.isInstance(bean)){
							// its factory bean.
							FactoryBean factoryBean=(FactoryBean) bean;
							Ehcache cache=null;
							try {
								cache= (Ehcache) factoryBean.getObject();
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
								throw new RuntimeException(e);
							}
							
							SpringEhcacheAware defaultSpringBeanEhcacheServiceImpl=
									getBeanByName("defaultSpringBeanEhcacheServiceImpl", SpringEhcacheAware.class);
							defaultSpringBeanEhcacheServiceImpl.putEhcache(cache);
							return (EhcacheService) defaultSpringBeanEhcacheServiceImpl;
						}
						else{
							LOGGER.info("cache bean found:"+bean.getClass().getName());
						}
					}
					else{
						// simple configure
						
					}
				}
				// use default .
				JEhcacheServiceAware defaultEhcacheServiceImpl=
						getBeanByName("defaultEhcacheServiceImpl", JEhcacheServiceAware.class);
				JDefaultEhcacheService defaultEhcacheService=new JDefaultEhcacheService(ehcacheServiceConfigure);
				defaultEhcacheServiceImpl.setEhcacheService(defaultEhcacheService);
				
				this.ehcacheService= (EhcacheService) defaultEhcacheServiceImpl;
			}
		}
		return ehcacheService;
	}
	
	/* (non-Javadoc)
	 * @see j.jave.framework.servicehub.JAbstractServiceFactory#getName()
	 */
	@Override
	public String getName() {
		return "Ehcache Provider";
	}
	
}
