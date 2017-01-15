/**
 * 
 */
package com.youappcorp.project.resourcemanager.sql;

import java.util.List;

import me.bunny.app._c.sps.core.autoloader.AbstractSQLDDLLoader;

/**
 * @author J
 */
public class ResourceSQLDDLLoader extends AbstractSQLDDLLoader{

	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.sql.SQLLoader#load()
	 */
	@Override
	public List<String> load() {
		try{
			return analyze(ResourceSQLDDLLoader.class.getResource("resource.sql").toURI());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
