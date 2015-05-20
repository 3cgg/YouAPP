/**
 * 
 */
package j.jave.framework.components.web.multi.platform.support;

/**
 * the class warps standard information to make the end-user 
 * know what is the response according to the request just sent by client.
 * @author J
 */
public class FilterResponse {

	private class KEY{
		static final String NO_LOGIN="NO_LOGIN";
		static final String NO_ACCESS="NO_ACCESS";
		static final String INVALID_PATH="INVALID_RESOURCE";
		static final String MULTI_REQUEST="MULTI_REQUEST";
	}
	
	public static FilterResponse newInvalidPath(){
		FilterResponse filterResponse=new FilterResponse();
		filterResponse.setKey(KEY.INVALID_PATH);
		return filterResponse;
	}
	
	public static FilterResponse newNoLogin(){
		FilterResponse filterResponse=new FilterResponse();
		filterResponse.setKey(KEY.NO_LOGIN);
		return filterResponse;
	}
	
	public static FilterResponse newNoAccess(){
		FilterResponse filterResponse=new FilterResponse();
		filterResponse.setKey(KEY.NO_ACCESS);
		return filterResponse;
	}
	
	public static FilterResponse newMultiRequest(){
		FilterResponse filterResponse=new FilterResponse();
		filterResponse.setKey(KEY.MULTI_REQUEST);
		return filterResponse;
	}
	
	/**
	 * the key is standard
	 */
	private String key;
	
	/**
	 * the detail information
	 */
	private Object object;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	
	
}
