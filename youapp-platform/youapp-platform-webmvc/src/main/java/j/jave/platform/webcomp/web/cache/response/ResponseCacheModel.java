/**
 * 
 */
package j.jave.platform.webcomp.web.cache.response;

/**
 * wrapper request path  and response byte array. 
 * @author J
 */
public class ResponseCacheModel {

	/**
	 * the request path  from <code>HTTPServletRequest</code>
	 */
	private String path;
	
	/**
	 * the content of JSP page ,  js , image , css 
	 */
	private Object object;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals=false;
		ResponseCacheModel another=(ResponseCacheModel) obj;
		if(path.equals(another.getPath())){
			equals= true;
		}
		return equals;
	}

	@Override
	public int hashCode() {
		return this.path.hashCode();
	}
	
	
	
	
	
	
	
	
	
	
	
}
