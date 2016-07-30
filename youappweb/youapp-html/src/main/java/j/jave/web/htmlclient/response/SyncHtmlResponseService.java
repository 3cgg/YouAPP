package j.jave.web.htmlclient.response;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.web.htmlclient.HtmlDefNames;
import j.jave.web.htmlclient.HtmlService;
import j.jave.web.htmlclient.SyncHtmlModel;
import j.jave.web.htmlclient.form.DefaultVoidDuplicateSubmitService;
import j.jave.web.htmlclient.form.VoidDuplicateSubmitService;
import j.jave.web.htmlclient.request.RequestHtml;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public class SyncHtmlResponseService {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(SyncHtmlResponseService.class);
	
	private HtmlService htmlService=HtmlService.get();
	
	private SyncHtmlResponseService(){}
	
	private static SyncHtmlResponseService INSTANCE=new SyncHtmlResponseService();

	private VoidDuplicateSubmitService voidDuplicateSubmitService=new DefaultVoidDuplicateSubmitService();
	
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
			String uri=requestUrl.getHtmlUrl();
			String paramMark="?param=";
			int paramMarkIndex=-1;
			if((paramMarkIndex=uri.indexOf(paramMark))!=-1){
				String paramJson=uri.substring(paramMarkIndex+paramMark.length());
				syncHtmlResponse.setHtmlParam(paramJson);
			}
			
			syncHtmlResponse.setToken(JJSON.get().formatObject(voidDuplicateSubmitService.newFormIdentification()));
			
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
