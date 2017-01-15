package com.youappcorp.project.containermanager.controller;

import j.jave.kernal.zookeeper.JCreateMode;
import j.jave.kernal.zookeeper.JZooKeeperNode;
import j.jave.kernal.zookeeper.JZooKeeperService;
import j.jave.platform.webcomp.rhttp.DefaultRemoteHttpDeployService;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.container.ContainerMappingMeta;
import j.jave.platform.webcomp.web.youappmvc.container.HttpInvokeContainerDelegateService;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;
import me.bunny.app._c.data.web.mapping.MappingMeta;
import me.bunny.app._c.sps.multiv.ComponentMetaNames;
import me.bunny.app._c.sps.multiv.ComponentVersionSpringApplicationSupport.Component;
import me.bunny.app._c.sps.multiv.ComponentVersionSpringApplicationSupport.ComponentProperties;
import me.bunny.app._c.sps.multiv.jnterface.JKey;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.async.JAsyncExecutor;
import me.bunny.kernel._c.async.JAsyncTaskExecutingService;
import me.bunny.kernel._c.io.JInputStreamWrapperSource;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JSimplePageable;
import me.bunny.kernel._c.support._resource.JJARResourceURIScanner;
import me.bunny.kernel._c.utils.JCollectionUtils;
import me.bunny.kernel._c.utils.JObjectUtils;
import me.bunny.kernel._c.utils.JPropertiesUtils;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.http.JHttpType;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youappcorp.project.containermanager.ContainerNames;
import com.youappcorp.project.containermanager.ContainerNames.DeployType;
import com.youappcorp.project.containermanager.model.AppMeta;
import com.youappcorp.project.containermanager.model.AppMetaRecord;
import com.youappcorp.project.containermanager.model.ModuleState;
import com.youappcorp.project.containermanager.model.URLMappingMeta;
import com.youappcorp.project.containermanager.model.URLMappingMetaRecord;
import com.youappcorp.project.containermanager.service.ContainerManagerService;
import com.youappcorp.project.containermanager.vo.AppMetaCriteria;
import com.youappcorp.project.containermanager.vo.AppMetaRecordVO;
import com.youappcorp.project.containermanager.vo.DeployModule;
import com.youappcorp.project.containermanager.vo.URLMappingMetaCriteria;
import com.youappcorp.project.containermanager.vo.URLMappingMetaRecordVO;
import com.youappcorp.project.runtimeurl.service.RuntimeUrlManagerService;

@Controller
@RequestMapping(value="/containermanager")
public class ContainerManagerController extends SimpleControllerSupport {

	@Autowired
	private ContainerManagerService containerManagerService;
	
	private DefaultRemoteHttpDeployService defaultRemoteHttpDeployService=
			JServiceHubDelegate.get().getService(this,DefaultRemoteHttpDeployService.class);
	
	private JAsyncTaskExecutingService asyncTaskExecutingService=JServiceHubDelegate.get().getService(this, JAsyncTaskExecutingService.class);
	
	private HttpInvokeContainerDelegateService httpInvokeContainerDelegateService=
			JServiceHubDelegate.get().getService(this,HttpInvokeContainerDelegateService.class);
	
	
	private RuntimeUrlManagerService runtimeUrlManagerService=
			JServiceHubDelegate.get().getService(this,RuntimeUrlManagerService.class);
	
	private JZooKeeperService zooKeeperService=
			JServiceHubDelegate.get().getService(this, JZooKeeperService.class);
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping(value="/deployJar")
	public ResponseModel deployJar( DeployModule deployModule) throws Exception{
		String jarUri=deployModule.getJarUri();
		if(!deployModule.isOverride()){
			JJARResourceURIScanner resourceURIScanner=new JJARResourceURIScanner(new URL(jarUri).toURI());
			resourceURIScanner.setRelativePath(Component.CONFIG_LOCATION);
			resourceURIScanner.setRecurse(true);
			resourceURIScanner.setIncludeFileName(ComponentProperties.PROPERTY_FILE);
			List<URI> uris=resourceURIScanner.scan();
			if(JCollectionUtils.hasInCollect(uris)){
				for(URI uri:uris){
					JInputStreamWrapperSource inputStreamWrapperSource=new JInputStreamWrapperSource(uri.toURL().openStream());
					Properties properties= JPropertiesUtils.loadProperties(inputStreamWrapperSource);
					String app=JPropertiesUtils.getKey(ComponentMetaNames.APP_NAME, properties);
					String component=JPropertiesUtils.getKey(ComponentMetaNames.COMPONENT_NAME, properties);
					String version=JPropertiesUtils.getKey(ComponentMetaNames.COMPONENT_VERSION, properties);
					boolean exists=containerManagerService.existsAppMeta( app, component, version);
					if(exists){
						return ResponseModel.newError().setData( "["+JKey.unique(app, component, version) +"] already exists.");
					}
				}
			}
		}
		
		
		
		String unique=defaultRemoteHttpDeployService.deployJar(jarUri);
		asyncTaskExecutingService.addAsyncTask(new JAsyncExecutor() {
			@Override
			public Object execute(Object data) throws Exception {
				runtimeUrlManagerService.cleanup();
				return null;
			}
		});
		
		ContainerMappingMeta containerMappingMeta= httpInvokeContainerDelegateService.getRuntimeMappingMeta(unique);
		JKey key=JKey.parse(unique);
		String appName=key.getApp();
		String appCompName=key.getComponent();
		String appVersion=key.getVersion();
		AppMeta appMeta=new AppMeta();
		appMeta.setAppName(appName);
		appMeta.setAppCompName(appCompName);
		appMeta.setAppVersion(appVersion);
		appMeta.setAppActive("Y");
		appMeta.setAppJarUrl(jarUri);
		appMeta.setDeployType(DeployType.JAR);
		appMeta.setAppUnique(unique);
		List<URLMappingMeta> urlMappingMetas=new ArrayList<URLMappingMeta>();
		Collection<MappingMeta> mappingMetas = containerMappingMeta.getMappingMetas();
		for(MappingMeta mappingMeta:mappingMetas){
			String url=mappingMeta.getPath();
			URLMappingMeta urlMappingMeta=new URLMappingMeta();
			urlMappingMeta.setUrl(url);
			urlMappingMeta.setUrlActive("Y");
			urlMappingMeta.setUrlType(JHttpType.GET.getValue());
			urlMappingMetas.add(urlMappingMeta);
		}
		
		boolean exists=containerManagerService.existsAppMeta( unique);
		if(exists){
			containerManagerService.updateAppMeta( appMeta, urlMappingMetas);
		}else{
			containerManagerService.saveAppMeta( appMeta, urlMappingMetas);
		}
		
		//SYNC ZOOKEEPER
		JConfiguration configuration=JConfiguration.get();
		String root=configuration.getString(ContainerNames.YOUAPPMVC_HTML_ROOT_IN_ZOOKEEPER,
				"/youapp/view");
		JZooKeeperNode zooKeeperNode=new JZooKeeperNode();
		zooKeeperNode.setPath(root);
		zooKeeperNode.setValue("it's root for html scanning.");
		zooKeeperNode.setCreateMode(JCreateMode.PERSISTENT);
		zooKeeperService.createDir(zooKeeperNode, true);
		
		zooKeeperNode=new JZooKeeperNode();
		zooKeeperNode.setPath(root+"/"+unique);
		ModuleState moduleState=new ModuleState();
		moduleState.setJarUrl(jarUri);
		moduleState.setActive(true);
		moduleState.setTime(new Date().getTime());
		String val=JJSON.get().formatObject(moduleState);
		if(!zooKeeperService.exist(zooKeeperNode)){
			zooKeeperNode.setValue(val);
			zooKeeperService.createNode(zooKeeperNode);
		}else{
			zooKeeperService.setNode(zooKeeperNode, val.getBytes("utf-8"));
		}
		
		return ResponseModel.newSuccess().setData(true);
	}
	
	@ResponseBody
	@RequestMapping(value="/getAppMetas")
	public ResponseModel getAppMetas(){
		List<AppMetaRecord> appMetas= containerManagerService.getAppMetas();
		return ResponseModel.newSuccess().setData(toAppMetaRecordVOs(appMetas));
	}
	
	private AppMetaRecordVO toAppMetaRecordVO(AppMetaRecord appMetaRecord){
		return JObjectUtils.simpleCopy(appMetaRecord, AppMetaRecordVO.class);
	}
	
	
	private List<AppMetaRecordVO> toAppMetaRecordVOs(List<AppMetaRecord> appMetaRecords){
		List<AppMetaRecordVO> appMetaRecordVOs=new ArrayList<AppMetaRecordVO>();
		for(AppMetaRecord appMetaRecord:appMetaRecords){
			appMetaRecordVOs.add(toAppMetaRecordVO(appMetaRecord));
		}
		return appMetaRecordVOs;
	}
	
	private void toAppMetaRecordVOPage(JPage<AppMetaRecord> appMetaRecordPage){
		appMetaRecordPage.setContent(toAppMetaRecordVOs(appMetaRecordPage.getContent()));
	}
	
	
	private URLMappingMetaRecordVO toURLMappingMetaRecordVO(URLMappingMetaRecord urlMappingMetaRecord){
		return JObjectUtils.simpleCopy(urlMappingMetaRecord, URLMappingMetaRecordVO.class);
	}
	
	
	private List<URLMappingMetaRecordVO> toURLMappingMetaRecordVOs(List<URLMappingMetaRecord> urlMappingMetaRecords){
		List<URLMappingMetaRecordVO> urlMappingMetaRecordVOs=new ArrayList<URLMappingMetaRecordVO>();
		for(URLMappingMetaRecord urlMappingMetaRecord:urlMappingMetaRecords){
			urlMappingMetaRecordVOs.add(toURLMappingMetaRecordVO(urlMappingMetaRecord));
		}
		return urlMappingMetaRecordVOs;
	}
	
	private void toURLMappingMetaRecordVOPage(JPage<URLMappingMetaRecord> urlMappingMetaRecordPage){
		urlMappingMetaRecordPage.setContent(toURLMappingMetaRecordVOs(urlMappingMetaRecordPage.getContent()));
	}
	
	@ResponseBody
	@RequestMapping(value="/getURLMappingMetasByAppId")
	public ResponseModel getURLMappingMetasByAppId(String appId){
		List<URLMappingMetaRecord> urlMappingMetas= containerManagerService.getURLMappingMetasByAppId( appId);
		return ResponseModel.newSuccess().setData(toURLMappingMetaRecordVOs(urlMappingMetas));
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getURLMappingMetasByAppConfig")
	public ResponseModel getURLMappingMetasByAppConfig(AppMetaRecordVO appMetaVO){
		List<URLMappingMetaRecord> urlMappingMetas= containerManagerService.getURLMappingMetasByAppConfig( 
				appMetaVO.getAppName(), appMetaVO.getAppCompName(), appMetaVO.getAppVersion());
		return ResponseModel.newSuccess().setData(toURLMappingMetaRecordVOs(urlMappingMetas));
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getAppMetaByAppConfig")
	public ResponseModel getAppMetaByAppConfig(AppMetaRecordVO appMetaVO){
		AppMetaRecord appMeta= containerManagerService.getAPPMetaByConfig( 
				appMetaVO.getAppName(), appMetaVO.getAppCompName(), appMetaVO.getAppVersion());
		return ResponseModel.newSuccess().setData(toAppMetaRecordVO(appMeta));
	}
	
	@ResponseBody
	@RequestMapping(value="/getAppMetaByAppId")
	public ResponseModel getAppMetaByAppId(String appId){
		AppMetaRecord appMeta= containerManagerService.getAPPMetaByAppId( appId);
		return ResponseModel.newSuccess().setData(toAppMetaRecordVO(appMeta));
	}
	
	@ResponseBody
	@RequestMapping(value="/getURLMappingMetaById")
	public ResponseModel getURLMappingMetaById(String id){
		URLMappingMetaRecord urlMappingMeta= containerManagerService.getURLMappingMetaById( id);
		return ResponseModel.newSuccess().setData(toURLMappingMetaRecordVO(urlMappingMeta));
	}
	
	@ResponseBody
	@RequestMapping(value="/getURLMappingMetasByPage")
	public ResponseModel getURLMappingMetasByPage(URLMappingMetaCriteria urlMappingMetaCriteria,JSimplePageable simplePageable){
		JPage<URLMappingMetaRecord>  urlMappingMetaPage= containerManagerService.getURLMappingMetasByPage( urlMappingMetaCriteria,simplePageable);
		toURLMappingMetaRecordVOPage(urlMappingMetaPage);
		return ResponseModel.newSuccess().setData(urlMappingMetaPage);
	}

	@ResponseBody
	@RequestMapping(value="/getAppMetasByPage")
	public ResponseModel getAppMetasByPage(AppMetaCriteria appMetaCriteria,JSimplePageable simplePageable){
		JPage<AppMetaRecord>  appMetaPage= containerManagerService.getAppMetasByPage( appMetaCriteria,simplePageable);
		toAppMetaRecordVOPage(appMetaPage);
		return ResponseModel.newSuccess().setData(appMetaPage);
	}
	
	
}
