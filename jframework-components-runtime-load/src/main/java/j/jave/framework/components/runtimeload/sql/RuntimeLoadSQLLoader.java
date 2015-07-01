/**
 * 
 */
package j.jave.framework.components.runtimeload.sql;

import j.jave.framework.components.core.autoloader.AbstractSQLDDLLoader;

import java.util.List;

/**
 * @author J
 *
 */
public class RuntimeLoadSQLLoader extends AbstractSQLDDLLoader {

	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.sql.SQLLoader#load()
	 */
	@Override
	public List<String> load() {
		try{
			return analyze(RuntimeLoadSQLLoader.class.getResource("runtimeload.sql").toURI());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
