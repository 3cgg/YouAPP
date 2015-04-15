package j.jave.framework.components.support.memcached.subhub;

import j.jave.framework.memcached.JDefaultMemcachedDisServiceConfigure;

import java.util.List;
import java.util.Map;

/**
 * only configuration for memcached
 * @author J
 *
 */
public class DefaultMemcachedServiceConfiguration implements JDefaultMemcachedDisServiceConfigure {

	private Map<String, List<String>> storeAddes=null;
	
	private Map<String, List<String>> backupAddes=null;
	
	@Override
	public void setStoreAddes(Map<String, List<String>> storeAddes) {
		this.storeAddes=storeAddes;
	}
	
	@Override
	public void setBackupAddes(Map<String, List<String>> backupAddes) {
		this.backupAddes=backupAddes;
	}
	
	public Map<String, List<String>> getBackupAddes() {
		return backupAddes;
	}
	
	public Map<String, List<String>> getStoreAddes() {
		return storeAddes;
	}

}
