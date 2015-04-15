/**
 * 
 */
package j.jave.framework.components.login.sql;

import j.jave.framework.components.core.autoloader.AbstractSQLDDLLoader;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class LoginSQLLoader extends AbstractSQLDDLLoader {

	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.sql.SQLLoader#load()
	 */
	@Override
	public List<String> load() {
		try{
			return analyze(LoginSQLLoader.class.getResource("login.sql").toURI());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
