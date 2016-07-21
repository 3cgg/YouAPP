package j.jave.web.htmlclient.thymeleaf;

import j.jave.web.htmlclient.HtmlFileService;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class ThymeleafHtmlFileService implements HtmlFileService {

	private static TemplateEngine templateEngine=ServletTemplateResolver.getTemplateEngine();
	
	
	@Override
	public byte[] getHtmlFile(String uri, Map<String, Object> attrs) {
		Context context=new Context(Locale.getDefault(), attrs);
		try {
			return templateEngine.process(uri, context).getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	@Override
	public byte[] getHtmlFileDef(String uri, Map<String, Object> attrs) {
		return null;
	}

	@Override
	public File getFile(String uri) {
		return null;
	}

	
}
