/**
 * 
 */
package com.youappcorp.project.usermanager.sql;

import j.jave.platform.sps.core.autoloader.AbstractSQLDMLLoader;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class LoginSQLDMLLoader extends AbstractSQLDMLLoader {

	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.sql.SQLLoader#load()
	 */
	@Override
	public List<String> load() {
		try{
			return analyze(LoginSQLDMLLoader.class.getResource("login-initialized.sql").toURI());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
