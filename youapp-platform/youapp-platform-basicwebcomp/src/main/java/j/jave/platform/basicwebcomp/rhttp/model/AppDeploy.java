package j.jave.platform.basicwebcomp.rhttp.model;

import j.jave.kernal.jave.model.JModel;

import java.util.List;

public class AppDeploy implements JModel {

	private AppDeployMeta appMeta;
	
	private List<URLMappingDeployMeta> urlMappingMetas;

	public AppDeployMeta getAppMeta() {
		return appMeta;
	}

	public void setAppMeta(AppDeployMeta appMeta) {
		this.appMeta = appMeta;
	}

	public List<URLMappingDeployMeta> getUrlMappingMetas() {
		return urlMappingMetas;
	}

	public void setUrlMappingMetas(List<URLMappingDeployMeta> urlMappingMetas) {
		this.urlMappingMetas = urlMappingMetas;
	}
	
}
