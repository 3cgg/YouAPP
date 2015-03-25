/**
 * 
 */
package j.jave.framework.components.param.sql;

import j.jave.framework.components.core.sql.SQLAnalyze;
import j.jave.framework.components.h2server.sql.SQLLoader;

import java.io.File;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class ParamSQLLoader implements SQLLoader {

	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.sql.SQLLoader#load()
	 */
	@Override
	public List<String> load() {
		try{
			File file=new File(ParamSQLLoader.class.getResource("param.sql").toURI());
			return SQLAnalyze.analyze(file);
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
