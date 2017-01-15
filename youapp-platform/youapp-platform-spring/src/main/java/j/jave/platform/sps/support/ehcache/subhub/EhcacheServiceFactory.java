/**
 * 
 */
package j.jave.platform.sps.support.ehcache.subhub;

import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import me.bunny.kernel.jave.utils.JAssert;
import net.sf.ehcache.Ehcache;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="EhcacheServiceFactory")
public class EhcacheServiceFactory extends SpringServiceFactorySupport<EhcacheDelegateService> {
	
	public EhcacheServiceFactory() {
		super(EhcacheDelegateService.class);
	}
	
	@Autowired(required=false)
	private SpringEhcacheConfiguration springEhcacheConfiguration;
	
	@Autowired
	@Qualifier(DefaultEhcacheDelegateService.BEAN_NAME)
	private EhcacheDelegateService defaultService;
	
	private EhcacheDelegateService ehcacheService;
	
	private Object sync=new Object();
	
	@Override
	public EhcacheDelegateService getService() {
		
		if(ehcacheService==null){
			synchronized (sync) {
				if(ehcacheService==null){
					// Ehcache may be held by spring. 
					if(springEhcacheConfiguration!=null){
						String factoryBeanName=springEhcacheConfiguration.getEhcacheFactoryBeanName();
						JAssert.isNotEmpty(factoryBeanName);
						Object bean=getBeanByName(factoryBeanName,Object.class);
						if(FactoryBean.class.isInstance(bean)){
							// its factory bean.
							FactoryBean<Ehcache> factoryBean=(FactoryBean<Ehcache>) bean;
							Ehcache cache=null;
							try {
								cache= factoryBean.getObject();
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
								throw new RuntimeException(e);
							}
							
							SpringEhcacheAware defaultSpringBeanEhcacheServiceImpl=
									getBeanByName(DefaultSpringBeanEhcacheServiceImpl.BEAN_NAME, 
											SpringEhcacheAware.class);
							defaultSpringBeanEhcacheServiceImpl.putEhcache(cache);
							this.ehcacheService= (EhcacheDelegateService) defaultSpringBeanEhcacheServiceImpl;
							return this.ehcacheService;
						}
						else{
							LOGGER.info("cache bean not found:"+bean.getClass().getName());
						}
					
					}
					else{
						// use default .
						this.ehcacheService= defaultService;
						return this.ehcacheService;
					}
				}
			}
		}
		return ehcacheService;
	}
	
	@Override
	public String getName() {
		return "Ehcache-Provider";
	}
	
}
