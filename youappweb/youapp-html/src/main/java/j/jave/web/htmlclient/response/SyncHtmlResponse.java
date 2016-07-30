package j.jave.web.htmlclient.response;


public class SyncHtmlResponse {

	private String html;
	
	private HtmlDefResponse htmlDef;
	
	/**
	 * JSON format 
	 */
	private String htmlParam;
	
	/**
	 * JSON format 
	 */
	private String token;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public HtmlDefResponse getHtmlDef() {
		return htmlDef;
	}

	public void setHtmlDef(HtmlDefResponse htmlDef) {
		this.htmlDef = htmlDef;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getHtmlParam() {
		return htmlParam;
	}

	public void setHtmlParam(String htmlParam) {
		this.htmlParam = htmlParam;
	}
	
}
