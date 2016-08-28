package j.jave.kernal.memcached;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JDefaultMemcachedDisServiceFactory extends
		JServiceFactorySupport<JDefaultMemcachedDisService> {

	private JDefaultMemcachedDisServiceImpl instance;
	
	private Object sync=new Object();
	
	@Override
	public Class<?> getServiceImplClass() {
		return JDefaultMemcachedDisServiceImpl.class;
	}
	
	@Override
	public JDefaultMemcachedDisServiceImpl doGetService() {
		
		if(instance==null){
			synchronized (sync) {
				if(instance==null){
					JConfiguration configuration= JConfiguration.get();
					DefaultMemcachedServiceConfiguration defaultConfig=new DefaultMemcachedServiceConfiguration();
					//youapp.memcache.store.address.group (JMemcacheProperties.MEMCACHE_STORE_ADDRESS_GROUP_PREFIX)
					defaultConfig.setStoreAddes(getStoreAddress(configuration, JMemcacheProperties.MEMCACHE_STORE_ADDRESS_GROUP_PREFIX));
					
					//youapp.memcache.backup.address.group (JMemcacheProperties.MEMCACHE_BACKUP_ADDRESS_GROUP_PREFIX)
					defaultConfig.setBackupAddes(getStoreAddress(configuration, JMemcacheProperties.MEMCACHE_BACKUP_ADDRESS_GROUP_PREFIX));
					instance=new JDefaultMemcachedDisServiceImpl(defaultConfig);
				}
			}
		}
		return instance;
	}

	private Map<String, List<String>> getStoreAddress(JConfiguration configuration,
			String groupAddress) {
		Set<String> keys=configuration.allKeys("^"+groupAddress.replace(".", "[.]")+".+$");
		Map<String, List<String>> storeAddes =new HashMap<String, List<String>>();
		for(String key:keys){
			String addressString=configuration.getString(key, null);
			if(JStringUtils.isNotNullOrEmpty(addressString)){
				String groupNameKey=key.substring(groupAddress.length()+1);
				List<String> addresses= JStringUtils.toStringList(addressString, ";");
				if(addresses.isEmpty()){
					LOGGER.warn(" MEMCACHE ADDRESS GROUP : "+groupNameKey+" empty.");
				}
				storeAddes.put(groupNameKey, addresses);
			}
		}
		return storeAddes;
	}
	
	@Override
	protected boolean isCanRegister() {
		return JConfiguration.get().getBoolean(JMemcacheProperties.DEFAULT_MEMCACHE_ENABLE, false);
	}
	
}
