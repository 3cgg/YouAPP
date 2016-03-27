/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.filter;

import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.model.ResponseStatus;

/**
 * the class warps standard information to make the end-user 
 * know what is the response according to the request just sent by client.
 * @author J
 */
@SuppressWarnings("serial")
public class FilterResponse extends ResponseModel{
	
	public static FilterResponse newInvalidPath(){
		FilterResponse filterResponse=new FilterResponse();
		filterResponse.setStatus(ResponseStatus.INVALID_PATH);
		return filterResponse;
	}
	
	public static FilterResponse newNoLogin(){
		FilterResponse filterResponse=new FilterResponse();
		filterResponse.setStatus(ResponseStatus.NO_LOGIN);
		return filterResponse;
	}
	
	public static FilterResponse newNoAccess(){
		FilterResponse filterResponse=new FilterResponse();
		filterResponse.setStatus(ResponseStatus.NO_ACCESS);
		return filterResponse;
	}
	
	public static FilterResponse newLinkedRequest(){
		FilterResponse filterResponse=new FilterResponse();
		filterResponse.setStatus(ResponseStatus.LINKED_REQUEST);
		return filterResponse;
	}
	
	public static FilterResponse newDuplicateLogin(){
		FilterResponse filterResponse=new FilterResponse();
		filterResponse.setStatus(ResponseStatus.DUPLICATE_LOGIN);
		return filterResponse;
	}
	
	public static FilterResponse newSuccessLogin(){
		FilterResponse filterResponse=new FilterResponse();
		filterResponse.setStatus(ResponseStatus.SUCCESS_LOGIN);
		return filterResponse;
	}
	
}
