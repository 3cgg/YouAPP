package j.jave.web.htmlclient;

import j.jave.web.htmlclient.form.FormIdentification;
import j.jave.web.htmlclient.request.RequestHtml;
import j.jave.web.htmlclient.response.HtmlDefResponse;
import j.jave.web.htmlclient.response.SyncHtmlResponse;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;

import java.io.UnsupportedEncodingException;


public class SyncHtmlResponseService {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(SyncHtmlResponseService.class);
	
	private HtmlService htmlService=JServiceHubDelegate.get().getService(this, HtmlService.class);
	
	private SyncHtmlResponseService(){}
	
	private static SyncHtmlResponseService INSTANCE=new SyncHtmlResponseService();

	private TokenGeneratorStrategy tokenGeneratorStrategy=new SimpleTokenGeneratorStrategy();
	
	public static SyncHtmlResponseService get(){
		return INSTANCE;
	}
	
	public SyncHtmlResponse error(RequestHtml requestUrl){
		
		SyncHtmlResponse syncHtmlResponse=new SyncHtmlResponse();
		
		HtmlDefResponse htmlDefResponse=new HtmlDefResponse();
		htmlDefResponse.setType(HtmlDefNames.HTML);
		htmlDefResponse.setLayoutId(requestUrl.getLayoutId());
		syncHtmlResponse.setHtmlDef(htmlDefResponse);
		
		try {
			syncHtmlResponse.setHtml(new String(htmlService.get404Page().getHtml(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
		}
		return syncHtmlResponse;
	}
	
	public SyncHtmlResponse getSyncHtmlResponse(RequestHtml requestUrl ,SyncHtmlModel syncHtmlModel){
		try{
			SyncHtmlResponse syncHtmlResponse=new SyncHtmlResponse();
			syncHtmlResponse.setHtml(new String(syncHtmlModel.getHtml(),"utf-8"));
			
			HtmlDefResponse htmlDefResponse=new HtmlDefResponse();
			htmlDefResponse.setType(syncHtmlModel.getHtmlDef().getType());
			htmlDefResponse.setLayoutId(requestUrl.getLayoutId());
			syncHtmlResponse.setHtmlDef(htmlDefResponse);
			syncHtmlResponse.setHtmlParam(requestUrl.getViewParam());
			FormIdentification formIdentification=tokenGeneratorStrategy.newFormIdentification(requestUrl);
			if(formIdentification!=null){
				syncHtmlResponse.setToken(JJSON.get().formatObject(formIdentification));
			}
			return syncHtmlResponse;
			
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return  error(requestUrl);
		}
		
	}
	
	
	public SyncHtmlResponse miniError(String message){
		
		SyncHtmlResponse syncHtmlResponse=new SyncHtmlResponse();
		
		HtmlDefResponse htmlDefResponse=new HtmlDefResponse();
		htmlDefResponse.setType(HtmlDefNames.HTML);
		htmlDefResponse.setLayoutId("global-mini-error");
		syncHtmlResponse.setHtmlDef(htmlDefResponse);
		syncHtmlResponse.setHtml("error occurs,please contact administrator. cause by "+message);
		return syncHtmlResponse;
	}
	
	
	
	
	
	
	
	
	
	
}
