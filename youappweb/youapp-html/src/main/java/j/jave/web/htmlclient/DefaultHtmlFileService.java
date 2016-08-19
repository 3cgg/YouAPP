package j.jave.web.htmlclient;

import j.jave.kernal.jave.utils.JFileUtils;

import java.io.File;
import java.util.Map;

public class DefaultHtmlFileService implements HtmlFileService {

	private String webappPath=null;
	{
		try{
			webappPath=j.jave.web.htmlclient.servlet.HtmlServlet.class.
					getClassLoader().getResource("../../").getPath();
		}catch(Exception e){
			webappPath="D:\\java_\\git-project\\YouAPP\\youappweb\\youapp-html\\src\\main\\webapp\\";
		}
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
		return new File(webappPath+uri);
	}

}
