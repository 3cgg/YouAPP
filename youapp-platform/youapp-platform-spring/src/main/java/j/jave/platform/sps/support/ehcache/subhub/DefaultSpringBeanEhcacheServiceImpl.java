/**
 * 
 */
package j.jave.platform.sps.support.ehcache.subhub;

import j.jave.kernal.ehcache.JAbstractEhcacheService;
import net.sf.ehcache.Ehcache;

import org.springframework.stereotype.Service;

/**
 * support spring-ehcache.  i.e.
 * <!-- 配置eh缓存管理器 -->
	<bean id="cacheManager"
		class="org.springframework.cache.ehcache.EhCacheManagerFactoryBean" >
			<property name="configLocation"  value="classpath:ehcache.xml"/>
		</bean>

	<!-- 配置一个简单的缓存工厂bean对象 -->
	<bean id="ehcacheSource" class="org.springframework.cache.ehcache.EhCacheFactoryBean">
		<property name="cacheName" value="meCache" />
		<property name="cacheManager"  ref="cacheManager" ></property>
 *</bean>
 * @author Administrator
 */
@Service(value=DefaultSpringBeanEhcacheServiceImpl.BEAN_NAME)
public class DefaultSpringBeanEhcacheServiceImpl extends JAbstractEhcacheService implements EhcacheDelegateService ,SpringEhcacheAware{
	
	static final String BEAN_NAME="defaultSpringBeanEhcacheServiceImpl";
	
	private static final String DEFAULT_CACHE_NAME="THE NAME IS NOT MISSING.";
	
	@Override
	public void putEhcache(Ehcache cache){
		ehcaches.put(DEFAULT_CACHE_NAME.hashCode(), cache);
	}

}
