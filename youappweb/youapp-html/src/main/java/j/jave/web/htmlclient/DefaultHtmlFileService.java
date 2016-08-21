package j.jave.web.htmlclient;

import j.jave.kernal.jave.utils.JFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultHtmlFileService implements HtmlFileService {

	private List<String> webappPaths=new ArrayList<String>();
	{
		String webappPath=null;
		try{
			webappPath=j.jave.web.htmlclient.servlet.HtmlServlet.class.
					getClassLoader().getResource("../../").getPath();
		}catch(Exception e){
			webappPath="D:\\java_\\git-project\\YouAPP\\youappweb\\youapp-html\\src\\main\\webapp\\";
		}
		webappPaths.add(webappPath);
		
		webappPaths.add("D:\\java_\\git-project\\YouAPP\\youappweb\\youapp-business\\youapp-business-bill\\src\\main\\resources");
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
