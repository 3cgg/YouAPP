package j.jave.web.htmlclient;

import j.jave.web.htmlclient.form.FormIdentification;
import j.jave.web.htmlclient.form.VoidDuplicateSubmitService;
import j.jave.web.htmlclient.request.RequestHtml;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SimpleTokenGeneratorStrategy implements TokenGeneratorStrategy {

	private VoidDuplicateSubmitService voidDuplicateSubmitService=
			JServiceHubDelegate.get().getService(this, VoidDuplicateSubmitService.class);
	private List<Pattern> includes=new ArrayList<Pattern>();
	
	private DefaultHtmlFileService defaultHtmlFileService=new DefaultHtmlFileService();
	{
		init();
	}
	
	private void init(){
		Map<String, Object> attrs=new HashMap<String, Object>();
		String fileDefName="/ui/pages/token.json";
		byte[] htmlDefBytes=defaultHtmlFileService.getHtmlFileDef(fileDefName,attrs);
		TokenConfig tokenConfig=null;
		try {
			tokenConfig= JJSON.get().parse(new String(htmlDefBytes,"utf-8"),TokenConfig.class);
		} catch (UnsupportedEncodingException e) {
		}
		List<PatternConfig> patternConfigs= tokenConfig.getInclude();
		for(PatternConfig patternConfig:patternConfigs){
			includes.add(Pattern.compile(patternConfig.getPattern()));
		}
	}
	
	private boolean matches(RequestHtml requestHtml){
		String html=requestHtml.getViewUrl();
		for(Pattern pattern:includes){
			if(pattern.matcher(html).matches()){
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public FormIdentification newFormIdentification(RequestHtml requestHtml) {
		if(matches(requestHtml)){
			return voidDuplicateSubmitService.newFormIdentification();
		}
		return null;
	}

}
