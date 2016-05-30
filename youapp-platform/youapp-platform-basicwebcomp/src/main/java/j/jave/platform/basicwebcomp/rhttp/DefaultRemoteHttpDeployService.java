package j.jave.platform.basicwebcomp.rhttp;

import j.jave.kernal.container.rhttp.JRemoteHttpContainerConfig;
import j.jave.kernal.container.rhttp.JRemoteURIInfo;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.http.JHttpType;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.platform.basicwebcomp.rhttp.model.AppDeploy;
import j.jave.platform.basicwebcomp.rhttp.model.AppDeployMeta;
import j.jave.platform.basicwebcomp.rhttp.model.URLMappingDeployMeta;
import j.jave.platform.basicwebcomp.web.youappmvc.container.HttpInvokeContainerDelegateService;
import j.jave.platform.multiversioncompsupportcomp.RemoteHttpComponentVersionApplication;

import java.net.URI;
import java.util.List;


/**
 * deploy/register remote HTTP.
 * @author JIAZJ
 *
 */
public class DefaultRemoteHttpDeployService
extends JServiceFactorySupport<DefaultRemoteHttpDeployService>
implements JService {

	private final static JLogger LOGGER=JLoggerFactory.getLogger(DefaultRemoteHttpDeployService.class);
	
	private HttpInvokeContainerDelegateService requestInvokeContainerDelegateService=
			JServiceHubDelegate.get().getService(this,HttpInvokeContainerDelegateService.class);
	
	
	/**
	 * deploy the APP into the container hub.
	 * @param app
	 * @return the container unique.
	 * @throws Exception
	 */
	public String deploy(AppDeploy app) throws Exception{
		
		AppDeployMeta appMeta=app.getAppMeta();
		
		RemoteHttpComponentVersionApplication versionApplication=new RemoteHttpComponentVersionApplication();
		versionApplication.setApp(appMeta.getAppName());
		versionApplication.setComponent(appMeta.getAppCompName());
		versionApplication.setVersion(appMeta.getAppVersion());
		versionApplication.setUrlPrefix("");
		versionApplication.setFriendlyUrl(appMeta.getFriendlyUrl());
		
		JRemoteHttpContainerConfig remoteHttpContainerConfig=new JRemoteHttpContainerConfig();
		remoteHttpContainerConfig.setName(versionApplication.getApp());
		remoteHttpContainerConfig.setUnique(versionApplication.unique());
		remoteHttpContainerConfig.setHost(appMeta.getAppHost());
		String unique=requestInvokeContainerDelegateService.newInstance(remoteHttpContainerConfig);
		LOGGER.info(appMeta.getAppUnique()+" has startup with the unique id ["+unique+"]");
		List<URLMappingDeployMeta> urlMappingMetas=app.getUrlMappingMetas();
		if(JCollectionUtils.hasInCollect(urlMappingMetas)){
			for(URLMappingDeployMeta urlMappingMeta:urlMappingMetas){
				String put=requestInvokeContainerDelegateService
						.getPutRequestURI(unique, urlMappingMeta.getUrl());
				JRemoteURIInfo remoteURIInfo=new JRemoteURIInfo();
				remoteURIInfo.setPath(urlMappingMeta.getUrl());
				remoteURIInfo.setHttpType(JHttpType.valueOf(urlMappingMeta.getUrlType().toUpperCase()));
				requestInvokeContainerDelegateService
					.execute(new URI(put), remoteURIInfo, unique, true);
			}
		}
		return unique;
	}
	
	
	/**
	 * delegate to {@link #deploy(AppDeploy)}
	 * @param appJson  the JSON format of {@link AppDeploy}
	 * @return  the container unique
	 * @throws Exception
	 */
	public String deploy(String appJson) throws Exception{
		return deploy(JJSON.get().parse(appJson, AppDeploy.class));
	}
	
	@Override
	public DefaultRemoteHttpDeployService getService() {
		return this;
	}
}
