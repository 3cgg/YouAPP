package j.jave.web.htmlclient;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.support._resource.JJARResourceURIScanner;
import j.jave.kernal.jave.utils.JIOUtils;
import j.jave.web.htmlclient.request.RequestHtml;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class HtmlService
extends JServiceFactorySupport<HtmlService>
implements JService , ModuleInstallListener{
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(HtmlService.class);
	
	private Map<String, TempHtmlDef> htmls=new ConcurrentHashMap<String, TempHtmlDef>();
	
	private HtmlFileService htmlFileService=new DefaultHtmlFileService();
	
	private HtmlViewDataResourceService htmlViewDataResourceService=new HtmlViewDataResourceService();
	
	
	private boolean useCache;

	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}
	
	public boolean isUseCache() {
		return useCache;
	}
	
	
	@Override
	protected HtmlService doGetService() {
		return this;
	}
	
	private static class TempHtmlDef{
		private SyncHtmlModel syncHtmlModel;
		private boolean active=true;
		private boolean inner=true;
	}
	
	
	@Override
	public Object trigger(ModuleInstallEvent event) {
		try{
			String moduleMetaStr=event.getModuleMeta();
			ModuleMeta moduleMeta= JJSON.get().parse(moduleMetaStr, ModuleMeta.class);
			JJARResourceURIScanner resourceURIScanner=new JJARResourceURIScanner(new URL(moduleMeta.getJarUrl()).toURI());
			resourceURIScanner.setRelativePath("ui/pages/");
			resourceURIScanner.setRecurse(true);
			resourceURIScanner.setIncludeExpression(".+[.]html$",".+[.]json$");
			List<URI> uris=resourceURIScanner.scan();
			Map<String, URI> map=new HashMap<String, URI>();
			for(URI uri:uris){
				map.put(uri.toString(), uri);
			}
			for(URI uri:uris){ 
				String uriStr=uri.toString();
				if(uri.toString().endsWith(".json")){
					continue;
				}
				SyncHtmlModel syncHtmlModel=new SyncHtmlModel();
				String jsonUri=uriStr.subSequence(0, uriStr.lastIndexOf(".html"))+".json";
				URI jsonURI=null;
				byte[] htmlDefBytes=null;
				if((jsonURI=map.get(jsonUri))!=null){
					htmlDefBytes=JIOUtils.getBytes(jsonURI.toURL().openStream(),true);
				}else{
					String fileDefName="/ui/pages/default.json";
					htmlDefBytes=htmlFileService.getHtmlFileDef(fileDefName,new HashMap<String, Object>());
				}
				HtmlDef htmlDef= JJSON.get().parse(new String(htmlDefBytes,"utf-8"), HtmlDef.class);
				syncHtmlModel.setHtmlDef(htmlDef);
				byte[] bytes=JIOUtils.getBytes(uri.toURL().openStream(),true);
				syncHtmlModel.setHtml(bytes);
				
				TempHtmlDef tempHtmlDef=new TempHtmlDef();
				tempHtmlDef.syncHtmlModel=syncHtmlModel;
				tempHtmlDef.inner=false;
				htmls.put(uri.toString().substring(uri.toString().indexOf(".jar!")+5), tempHtmlDef);
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return true; 
	}
	
	public SyncHtmlModel get404Page(){
		SyncHtmlModel syncHtmlModel=new SyncHtmlModel();
		syncHtmlModel.setHtml(htmlFileService.getHtmlFile("/ui/pages/404.html", new HashMap<String, Object>()));
		HtmlDef htmlDef=new HtmlDef();
		htmlDef.setType(HtmlDefNames.HTML);
		syncHtmlModel.setHtmlDef(htmlDef);
		return syncHtmlModel;
	}
	
	public SyncHtmlModel getSyncHtmlModel(RequestHtml requestHtml){
		try{
			String uri=requestHtml.getViewUrl();
			TempHtmlDef tempHtmlDef=null;
			
			if(useCache){
				if((tempHtmlDef=htmls.get(uri))!=null&&!tempHtmlDef.inner){
					return tempHtmlDef.syncHtmlModel;
				}
			}
			
//			File file=htmlFileService.getFile(uri);
//			String fileName=JFileUtils.getFileNameNoExtension(file);
			Map<String, Object> attrs=htmlViewDataResourceService.data(requestHtml);
			String fileDefName="/ui/pages/default.json";
			byte[] htmlDefBytes=htmlFileService.getHtmlFileDef(fileDefName,attrs);
			HtmlDef htmlDef= JJSON.get().parse(new String(htmlDefBytes,"utf-8"), HtmlDef.class);
			String layoutId=requestHtml.getLayoutId();
			if(layoutId!=null&&!layoutId.equals("")){
				
			}
			
			attrs.putAll(JJSON.get().parse(requestHtml.getViewParam()));
			SyncHtmlModel syncHtmlModel=new SyncHtmlModel();
			syncHtmlModel.setHtmlDef(htmlDef);
			syncHtmlModel.setHtml(htmlFileService.getHtmlFile(uri,attrs));
			
			tempHtmlDef=new TempHtmlDef();
			tempHtmlDef.syncHtmlModel=syncHtmlModel;
			htmls.put(uri, tempHtmlDef);
			return syncHtmlModel;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return get404Page();
		}
		
	}

	
}
