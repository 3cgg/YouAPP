package j.jave.web.htmlclient;

import j.jave.kernal.jave.json.JJSON;
import j.jave.web.htmlclient.request.RequestHtml;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class HtmlService {
	
	private Map<String, TempHtmlDef> htmls=new ConcurrentHashMap<String, TempHtmlDef>();
	
	private HtmlFileService htmlFileService=new DefaultHtmlFileService();
	
	private HtmlViewDataResourceService htmlViewDataResourceService=new HtmlViewDataResourceService();
	
	private static class TempHtmlDef{
		
		private SyncHtmlModel syncHtmlModel;
		
	}
	
	private HtmlService(){}
	
	private static HtmlService INSTANCE=new HtmlService();

	public static HtmlService get(){
		return INSTANCE;
	}
	
	
	public SyncHtmlModel get404Page(){
		return null;
	}
	
	public SyncHtmlModel getSyncHtmlModel(RequestHtml requestHtml){
		try{
			String uri=requestHtml.getHtmlUrl();
//			File file=htmlFileService.getFile(uri);
//			String fileName=JFileUtils.getFileNameNoExtension(file);
			Map<String, Object> attrs=htmlViewDataResourceService.data(requestHtml);
			String fileDefName="/ui/pages/default.json";
			byte[] htmlDefBytes=htmlFileService.getHtmlFileDef(fileDefName,attrs);
			HtmlDef htmlDef= JJSON.get().parse(new String(htmlDefBytes,"utf-8"), HtmlDef.class);
			String layoutId=requestHtml.getLayoutId();
			if(layoutId!=null&&!layoutId.equals("")){
				
			}
			String paramMark="?param=";
			int paramMarkIndex=-1;
			if((paramMarkIndex=uri.indexOf(paramMark))!=-1){
				String paramJson=uri.substring(paramMarkIndex+paramMark.length());
				attrs.putAll(JJSON.get().parse(paramJson));
				uri=uri.substring(0, paramMarkIndex);
			}
			SyncHtmlModel syncHtmlModel=new SyncHtmlModel();
			syncHtmlModel.setHtmlDef(htmlDef);
			syncHtmlModel.setHtml(htmlFileService.getHtmlFile(uri,attrs));
			
			TempHtmlDef tempHtmlDef=new TempHtmlDef();
			tempHtmlDef.syncHtmlModel=syncHtmlModel;
			
			htmls.put(uri, tempHtmlDef);
			return syncHtmlModel;
		}catch(Exception e){
			return get404Page();
		}
		
	}
	
	
	
}
