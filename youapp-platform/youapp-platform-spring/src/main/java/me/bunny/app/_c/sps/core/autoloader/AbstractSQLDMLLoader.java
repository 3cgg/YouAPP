/**
 * 
 */
package me.bunny.app._c.sps.core.autoloader;

import j.jave.kernal.sqlloader.dml.JSQLDMLLoader;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JStringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * abstract implementation for DML Loader. 
 * define a basic common strategy  or style to resolve the sql. 
 * @author Administrator
 *
 */
public abstract class AbstractSQLDMLLoader implements JSQLDMLLoader{

	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	/**
	 * default/common format 
	 * @param uri
	 * @return
	 */
	protected List<String> analyze(URI uri){
		InputStream imInputStream=null;
		try {
			imInputStream=uri.toURL().openStream();
			return analyze(imInputStream);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}finally{
			if(imInputStream!=null){
				try {
					imInputStream.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	private List<String> analyze(InputStream inputStream) throws Exception{
		List<String> sqls=new ArrayList<String>();
		String contentUTF8=new String(JStringUtils.getBytes(inputStream),"utf-8");
		String[] lines=contentUTF8.split("\r\n");
		for (int i = 0; i < lines.length; i++) {
			String line=lines[i];
			if(JStringUtils.isNotNullOrEmpty(line)){
				if(!line.startsWith("--")){
					sqls.add(line);
				}
			}
			
		}
		return sqls;
	}

}
