/**
 * 
 */
package com.youappcorp.project.resourcemanager.sql;

import java.util.List;

import me.bunny.app._c.sps.core.autoloader.AbstractSQLDMLLoader;

/**
 * @author J
 */
public class ResourceSQLDMLLoader extends AbstractSQLDMLLoader{

	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.sql.SQLLoader#load()
	 */
	@Override
	public List<String> load() {
		try{
			return analyze(ResourceSQLDMLLoader.class.getResource("resource-initialized.sql").toURI());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
