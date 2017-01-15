package j.jave.platform.webcomp.rhttp.model;

import me.bunny.kernel._c.model.JModel;


/**
 * @author J
 */
public class URLMappingDeployMeta implements JModel  {
	
	private String url;
	
	private String urlType;
	
	private String urlDesc;
	
	private String urlName;
	
	private String urlActive;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public String getUrlDesc() {
		return urlDesc;
	}

	public void setUrlDesc(String urlDesc) {
		this.urlDesc = urlDesc;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public String getUrlActive() {
		return urlActive;
	}

	public void setUrlActive(String urlActive) {
		this.urlActive = urlActive;
	}
	
}
