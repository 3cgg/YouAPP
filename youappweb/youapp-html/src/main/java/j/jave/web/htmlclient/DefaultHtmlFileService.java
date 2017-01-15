package j.jave.web.htmlclient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.bunny.kernel._c.utils.JFileUtils;
import me.bunny.kernel._c.utils.JStringUtils;

public class DefaultHtmlFileService implements HtmlFileService {

	private static List<String> webappPaths=new ArrayList<String>();
	static {
		String webappPath=null;
		try{
			webappPath=j.jave.web.htmlclient.servlet.HtmlServlet.class.
					getClassLoader().getResource("../../").getPath();
		}catch(Exception e){
		}
		webappPaths.add(webappPath);
	}
	
	public static void addHtmlPath(String webappPath){
		if(JStringUtils.isNotNullOrEmpty(webappPath)&&
				!webappPaths.contains(webappPath)){
			webappPaths.add(webappPath);
		}
	}
	public static void clearHtmlPath(){
		String one=webappPaths.get(0);
		webappPaths.clear();
		webappPaths.add(one);
	}
	
	public static String getHtmlPath(){
		String webappPath="";
		for(int i=1;i<webappPaths.size();i++){
			webappPath=webappPath+webappPaths.get(i)+";\r\n\r\n";
		}
		return webappPath;
	}
	
	@Override
	public byte[] getHtmlFile(String uri,Map<String, Object> attrs) {
		return JFileUtils.getBytes(getFile(uri));
	}

	@Override
	public byte[] getHtmlFileDef(String uri,Map<String, Object> attrs) {
		return JFileUtils.getBytes(getFile(uri));
	}
	
	public File getFile(String uri){
		File file=null;
		for(String webappPath:webappPaths){
			file=new File(webappPath+uri);
			if(file.exists()){
				return file;
			}
		}
		return null;
	}

}
