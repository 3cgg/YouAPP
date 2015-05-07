/**
 * 
 */
package j.jave.framework.components.memory.response.subhub;

/**
 * wrapper request path  and response byte array. 
 * @author J
 */
public class ResponseCachedResource {

	/**
	 * the request path  from <code>HTTPServletRequest</code>
	 */
	private String path;
	
	/**
	 * the content of JSP page ,  js , image , css 
	 */
	private byte[] bytes;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals=false;
		ResponseCachedResource another=(ResponseCachedResource) obj;
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
