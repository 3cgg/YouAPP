/**
 * 
 */
package j.jave.framework.components.memcached;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class MemcachedConfiguration {

	
	protected Map<String, List<String>> storeAddes ;
	protected Map<String, List<String>> backupAddes ;
	public Map<String, List<String>> getStoreAddes() {
		return storeAddes;
	}
	public void setStoreAddes(Map<String, List<String>> storeAddes) {
		this.storeAddes = storeAddes;
	}
	public Map<String, List<String>> getBackupAddes() {
		return backupAddes;
	}
	public void setBackupAddes(Map<String, List<String>> backupAddes) {
		this.backupAddes = backupAddes;
	}
	

	

	
	
	
}
