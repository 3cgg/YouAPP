package j.jave.web.htmlclient.request;

import j.jave.web.htmlclient.RequestContext;

public class RequestHtml {
	
	/**
	 * local html location
	 */
	private String htmlUrl;
	
	private String layoutId;
	
	/**
	 * get data from anywhere ,such as remote uri , local json data or database data etc.
	 */
	private String dataUrl;
	
	private transient RequestContext request;
	
	/**
	 * JSON format of HTML parameters
	 */
	private transient String viewParam;
	
	/**
	 * the request URL which contains HTML parameters/HTML location
	 */
	private transient String viewUrl;

	public RequestContext getRequest() {
		return request;
	}

	public void setRequest(RequestContext request) {
		this.request = request;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public String getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	/**
	 * {@link #dataUrl}
	 * @return
	 */
	public String getDataUrl() {
		return dataUrl;
	}

	/**
	 * {@link #dataUrl}
	 * @param dataUrl
	 */
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public String getViewParam() {
		return viewParam;
	}

	public void setViewParam(String viewParam) {
		this.viewParam = viewParam;
	}

	public String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	
}
