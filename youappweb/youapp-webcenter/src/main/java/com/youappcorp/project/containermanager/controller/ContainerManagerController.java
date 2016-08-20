package com.youappcorp.project.containermanager.controller;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.http.JHttpType;
import j.jave.kernal.jave.async.JAsyncExecutor;
import j.jave.kernal.jave.async.JAsyncTaskExecutingService;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.utils.JObjectUtils;
import j.jave.kernal.zookeeper.JZooKeeperNode;
import j.jave.kernal.zookeeper.JZooKeeperService;
import j.jave.platform.data.web.mapping.MappingMeta;
import j.jave.platform.sps.multiv.jnterface.JKey;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.rhttp.DefaultRemoteHttpDeployService;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.container.ContainerMappingMeta;
import j.jave.platform.webcomp.web.youappmvc.container.HttpInvokeContainerDelegateService;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	public ResponseModel deployJar(ServiceContext serviceContext, String jarUri) throws Exception{
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
		containerManagerService.saveAppMeta(serviceContext, appMeta, urlMappingMetas);
		
		//SYNC ZOOKEEPER
		JConfiguration configuration=JConfiguration.get();
		String root=configuration.getString(ContainerNames.YOUAPPMVC_HTML_ROOT_IN_ZOOKEEPER,
				"/youapp/view");
		JZooKeeperNode zooKeeperNode=new JZooKeeperNode();
		zooKeeperNode.setPath(root);
		zooKeeperNode.setValue("it's root for html scanning.");
		zooKeeperService.createDir(zooKeeperNode, true);
		
		zooKeeperNode=new JZooKeeperNode();
		zooKeeperNode.setPath(root+"/"+unique);
		ModuleState moduleState=new ModuleState();
		moduleState.setJarUrl(jarUri);
		moduleState.setActive(true);
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
	public ResponseModel getAppMetas(ServiceContext serviceContext){
		List<AppMetaRecord> appMetas= containerManagerService.getAppMetas(serviceContext);
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
	public ResponseModel getURLMappingMetasByAppId(ServiceContext serviceContext,String appId){
		List<URLMappingMetaRecord> urlMappingMetas= containerManagerService.getURLMappingMetasByAppId(serviceContext, appId);
		return ResponseModel.newSuccess().setData(toURLMappingMetaRecordVOs(urlMappingMetas));
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getURLMappingMetasByAppConfig")
	public ResponseModel getURLMappingMetasByAppConfig(ServiceContext serviceContext,AppMetaRecordVO appMetaVO){
		List<URLMappingMetaRecord> urlMappingMetas= containerManagerService.getURLMappingMetasByAppConfig(serviceContext, 
				appMetaVO.getAppName(), appMetaVO.getAppCompName(), appMetaVO.getAppVersion());
		return ResponseModel.newSuccess().setData(toURLMappingMetaRecordVOs(urlMappingMetas));
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getAppMetaByAppConfig")
	public ResponseModel getAppMetaByAppConfig(ServiceContext serviceContext,AppMetaRecordVO appMetaVO){
		AppMetaRecord appMeta= containerManagerService.getAPPMetaByConfig(serviceContext, 
				appMetaVO.getAppName(), appMetaVO.getAppCompName(), appMetaVO.getAppVersion());
		return ResponseModel.newSuccess().setData(toAppMetaRecordVO(appMeta));
	}
	
	@ResponseBody
	@RequestMapping(value="/getAppMetaByAppId")
	public ResponseModel getAppMetaByAppId(ServiceContext serviceContext,String appId){
		AppMetaRecord appMeta= containerManagerService.getAPPMetaByAppId(serviceContext, appId);
		return ResponseModel.newSuccess().setData(toAppMetaRecordVO(appMeta));
	}
	
	@ResponseBody
	@RequestMapping(value="/getURLMappingMetaById")
	public ResponseModel getURLMappingMetaById(ServiceContext serviceContext,String id){
		URLMappingMetaRecord urlMappingMeta= containerManagerService.getURLMappingMetaById(serviceContext, id);
		return ResponseModel.newSuccess().setData(toURLMappingMetaRecordVO(urlMappingMeta));
	}
	
	@ResponseBody
	@RequestMapping(value="/getURLMappingMetasByPage")
	public ResponseModel getURLMappingMetasByPage(ServiceContext serviceContext,URLMappingMetaCriteria urlMappingMetaCriteria,JSimplePageable simplePageable){
		JPage<URLMappingMetaRecord>  urlMappingMetaPage= containerManagerService.getURLMappingMetasByPage(serviceContext, urlMappingMetaCriteria,simplePageable);
		toURLMappingMetaRecordVOPage(urlMappingMetaPage);
		return ResponseModel.newSuccess().setData(urlMappingMetaPage);
	}

	@ResponseBody
	@RequestMapping(value="/getAppMetasByPage")
	public ResponseModel getAppMetasByPage(ServiceContext serviceContext,AppMetaCriteria appMetaCriteria,JSimplePageable simplePageable){
		JPage<AppMetaRecord>  appMetaPage= containerManagerService.getAppMetasByPage(serviceContext, appMetaCriteria,simplePageable);
		toAppMetaRecordVOPage(appMetaPage);
		return ResponseModel.newSuccess().setData(appMetaPage);
	}
	
	
}
