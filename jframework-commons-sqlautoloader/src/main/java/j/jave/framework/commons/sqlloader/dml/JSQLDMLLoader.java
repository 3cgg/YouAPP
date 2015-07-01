/**
 * 
 */
package j.jave.framework.commons.sqlloader.dml;

import java.util.List;

/**
 * only load DML SQL from .sql file.
 * @author Administrator
 *
 */
public interface JSQLDMLLoader {
	
	/**
	 * only load DML SQL from .sql file. 
	 * @return
	 */
	public List<String> load();
	
}
