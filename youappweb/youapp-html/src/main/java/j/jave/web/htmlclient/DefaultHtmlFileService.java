package j.jave.web.htmlclient;

import java.io.File;
import java.util.Map;

public class DefaultHtmlFileService implements HtmlFileService {

	private String webappPath=
			j.jave.web.htmlclient.servlet.HtmlServlet.class.
			getClassLoader().getResource("../../").getPath();
	
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
