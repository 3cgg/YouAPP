/**
 * 
 */
package j.jave.platform.sps.core.autoloader;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.sqlloader.ddl.JSQLDDLLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * abstract implementation for DDL Loader. 
 * define a basic common strategy  or style to resolve the sql. 
 * @author Administrator
 *
 */
public abstract class AbstractSQLDDLLoader implements JSQLDDLLoader{

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
		StringBuffer stringBuffer=new StringBuffer();
		for (int i = 0; i < lines.length; i++) {
			String line=lines[i];
			if(line.startsWith("--")&&line.endsWith("--")){
				sqls.add(stringBuffer.toString());
				stringBuffer=new StringBuffer();
			}
			String to=line;
			if(line.indexOf("--")!=-1){
				to=line.substring(0,line.indexOf("--"));
			}
			stringBuffer.append(to);
		}
		
		if(!"".equals(stringBuffer.toString())){
			sqls.add(stringBuffer.toString());
		}
		return sqls;
	}
	
	private List<String> analyze(File file){
		List<String> sqls=new ArrayList<String>();
		RandomAccessFile accessFile=null;
		try{
			accessFile=new RandomAccessFile(file, "r");
			String line=null;
			StringBuffer stringBuffer=new StringBuffer();
			while((line=accessFile.readLine())!=null){
				if(line.startsWith("--")&&line.endsWith("--")){
					sqls.add(stringBuffer.toString());
					stringBuffer=new StringBuffer();
				}
				String to=line;
				if(line.indexOf("--")!=-1){
					to=line.substring(0,line.indexOf("--"));
				}
				stringBuffer.append(to);
			}
			
			if(!"".equals(stringBuffer.toString())){
				sqls.add(stringBuffer.toString());
			}
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(accessFile!=null){
				try {
					accessFile.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return sqls;
	}
	
}
