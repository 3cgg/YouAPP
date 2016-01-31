/**
 * 
 */
package j.jave.kernal.sqlloader.ddl;

import java.util.List;

/**
 * only load DDL SQL from .sql file.
 * @author Administrator
 *
 */
public interface JSQLDDLLoader {
	
	/**
	 * only load DDL SQL from .sql file. 
	 * @return
	 */
	public List<String> load();
	
}
