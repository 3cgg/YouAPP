/**
 * 
 */
package com.youappcorp.project.param.sql;

import j.jave.platform.sps.core.autoloader.AbstractSQLDDLLoader;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class ParamSQLLoader extends AbstractSQLDDLLoader {

	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.sql.SQLLoader#load()
	 */
	@Override
	public List<String> load() {
		try{
			return analyze(ParamSQLLoader.class.getResource("param.sql").toURI());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		ParamSQLLoader loader=new ParamSQLLoader();
		List<String> sqls=loader.load();
		System.out.println(sqls);
	}
	
}
