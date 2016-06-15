/**
 * 
 */
package com.youappcorp.project.bill.sql;

import j.jave.platform.sps.core.autoloader.AbstractSQLDDLLoader;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class BillSQLLoader extends AbstractSQLDDLLoader {

	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.sql.SQLLoader#load()
	 */
	@Override
	public List<String> load() {
		try{
			return analyze(BillSQLLoader.class.getResource("bill.sql").toURI());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
