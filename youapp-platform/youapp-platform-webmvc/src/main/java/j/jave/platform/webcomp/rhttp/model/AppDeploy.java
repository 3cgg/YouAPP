package j.jave.platform.webcomp.rhttp.model;

import java.util.List;

import me.bunny.kernel._c.model.JModel;

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
