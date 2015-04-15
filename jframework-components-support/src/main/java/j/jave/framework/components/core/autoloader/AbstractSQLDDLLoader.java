/**
 * 
 */
package j.jave.framework.components.core.autoloader;

import j.jave.framework.sqlloader.JSQLDDLLoader;
import j.jave.framework.utils.JUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * abstract implementation for DDL Loader. 
 * define a basic common strategy  or style to resolve the sql. 
 * @author Administrator
 *
 */
public abstract class AbstractSQLDDLLoader implements JSQLDDLLoader{

	protected final Logger LOGGER=LoggerFactory.getLogger(getClass());
	
	protected List<String> analyze(URI uri){
		try {
			return analyze(uri.toURL().openStream());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	
	private List<String> analyze(InputStream inputStream){
		List<String> sqls=new ArrayList<String>();
		try{
			String contentUTF8=new String(JUtils.getBytes(inputStream),"utf-8");
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
			
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
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
	
	public static void main(String[] args) throws Exception {
		String path="file:\\D:\\java_\\so\\sources\\trunk\\jframework-me\\target\\jframework-me-1.0\\WEB-INF\\lib\\jframework-components-bill-1.0.jar!\\j\\jave\\framework\\components\\bill\\sql\\bill.sql".replace("\\", "/");
		URI uri=new URI(path);
		
		System.out.println(uri.getScheme());
		System.out.println(uri.getSchemeSpecificPart());
		
		System.out.println(new RandomAccessFile(new File(path.replace("file:/", "")), "r"));
		System.out.println("o");
		System.out.println(new RandomAccessFile(new File(path), "r"));
	}
	
	
}
