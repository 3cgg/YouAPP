package j.jave.web.htmlclient.request;

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
	
}
