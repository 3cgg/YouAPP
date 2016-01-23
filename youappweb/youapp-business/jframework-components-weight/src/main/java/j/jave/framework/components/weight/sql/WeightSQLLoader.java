/**
 * 
 */
package j.jave.framework.components.weight.sql;

import j.jave.framework.components.core.autoloader.AbstractSQLDDLLoader;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class WeightSQLLoader extends AbstractSQLDDLLoader {

	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.sql.SQLLoader#load()
	 */
	@Override
	public List<String> load() {
		try{
			return analyze(WeightSQLLoader.class.getResource("weight.sql").toURI());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	
}
