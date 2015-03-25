/**
 * 
 */
package j.jave.framework.components.core.sql;

import j.jave.framework.components.login.sql.LoginSQLLoader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class SQLAnalyze {

	
	public static List<String> analyze(File file){
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
