/**
 * 
 */
package j.jave.framework.memcached;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public interface JDefaultMemcachedDisServiceConfigure {
	
	public void setStoreAddes(Map<String, List<String>> storeAddes);

	public void setBackupAddes(Map<String, List<String>> backupAddes);

}
