/**
 * 
 */
package com.youappcorp.project.resource.sql;

import j.jave.platform.sps.core.autoloader.AbstractSQLDDLLoader;

import java.util.List;

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
