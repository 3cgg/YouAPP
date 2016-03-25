/**
 * 
 */
package j.jave.platform.basicsupportcomp.support.ehcache.subhub;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.ehcache.JDefaultEhcacheService;
import j.jave.kernal.ehcache.JEhcacheServiceAware;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;
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
	private BeanSupportEhcacheServiceConfigure ehcacheServiceConfigure;
	
	
	private EhcacheService ehcacheService;
	
	private Object sync=new Object();
	
	@Override
	public EhcacheService getService() {
		
		if(ehcacheService==null){
			synchronized (sync) {
				
				// Ehcache may holded by spring. 
				if(ehcacheServiceConfigure!=null){
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
							this.ehcacheService= (EhcacheService) defaultSpringBeanEhcacheServiceImpl;
							return this.ehcacheService;
						}
						else{
							LOGGER.info("cache bean found:"+bean.getClass().getName());
						}
					}
					else{
						// simple configure
						
					}
				}
				else{
					// use default .
					JEhcacheServiceAware defaultEhcacheServiceImpl=
							getBeanByName("defaultEhcacheServiceImpl", JEhcacheServiceAware.class);
					JDefaultEhcacheService defaultEhcacheService=new JDefaultEhcacheService(JConfiguration.get());
					defaultEhcacheServiceImpl.setEhcacheService(defaultEhcacheService);
					this.ehcacheService= (EhcacheService) defaultEhcacheServiceImpl;
					return this.ehcacheService;
				}
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
