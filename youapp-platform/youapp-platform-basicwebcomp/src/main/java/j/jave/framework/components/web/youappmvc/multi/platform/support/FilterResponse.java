/**
 * 
 */
package j.jave.framework.components.web.youappmvc.multi.platform.support;

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
		static final String LINKED_REQUEST="LINKED_REQUEST";
		static final String DUPLICATE_LOGIN="DUPLICATE_LOGIN";
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
	
	public static FilterResponse newLinkedRequest(){
		FilterResponse filterResponse=new FilterResponse();
		filterResponse.setKey(KEY.LINKED_REQUEST);
		return filterResponse;
	}
	
	public static FilterResponse newDuplicateLogin(){
		FilterResponse filterResponse=new FilterResponse();
		filterResponse.setKey(KEY.DUPLICATE_LOGIN);
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
