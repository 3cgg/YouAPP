package j.jave.kernal.container;

import j.jave.kernal.jave.model.JModel;

/**
 * the URI model for any executable URI associated to any container.
 * @author JIAZJ
 *
 */
public class JURIInfo implements JModel {

	private String wholeUri;
	
	private String scheme;
	
	private String host;
	
	private String path;
	
	private String queryUnique;
	
	private String queryPath;

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getQueryUnique() {
		return queryUnique;
	}

	public void setQueryUnique(String queryUnique) {
		this.queryUnique = queryUnique;
	}

	public String getQueryPath() {
		return queryPath;
	}

	public void setQueryPath(String queryPath) {
		this.queryPath = queryPath;
	}

	public String getWholeUri() {
		return wholeUri;
	}

	public void setWholeUri(String wholeUri) {
		this.wholeUri = wholeUri;
	}
	
}
