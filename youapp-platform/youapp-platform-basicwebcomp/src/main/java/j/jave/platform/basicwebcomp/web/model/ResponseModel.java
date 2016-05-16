package j.jave.platform.basicwebcomp.web.model;

import j.jave.kernal.jave.model.JModel;


@SuppressWarnings("serial")
public class ResponseModel implements JModel{
	
	/**
	 * return status.  OK! or MESSAGE! or ERROR! 
	 * 1 means OK , 0 means MESSAGE, -1 means ERROR. 
	 * etc....
	 */
	private ResponseStatus status;
	
	/**
	 * the data for the subview if status is OK , message for showing if status is ERROR.
	 */
	private Object data;

	public Object getData() {
		return data;
	}

	public ResponseModel setData(Object data) {
		this.data = data;
		return this;
	}
	
	public static ResponseModel newSuccess(){
		ResponseModel mobileResult=new ResponseModel();
		mobileResult.status=ResponseStatus.SUCCESS;
		return mobileResult;
	}

	public static ResponseModel newMessage(){
		ResponseModel mobileResult=new ResponseModel();
		mobileResult.status=ResponseStatus.MESSAGE;
		return mobileResult;
	}
	
	public static ResponseModel newError(){
		ResponseModel mobileResult=new ResponseModel();
		mobileResult.status=ResponseStatus.FAIL;
		return mobileResult;
	}

	/**
	 * return status.  OK! or MESSAGE! or ERROR! 
	 * 1 means OK , 0 means MESSAGE, -1 means ERROR. 
	 * etc....
	 */
	public ResponseStatus getStatus() {
		return status;
	}

	public ResponseModel setStatus(ResponseStatus status) {
		this.status = status;
		return this;
	}
	
	public static ResponseModel newInvalidPath(){
		ResponseModel filterResponse=new ResponseModel();
		filterResponse.setStatus(ResponseStatus.INVALID_PATH);
		return filterResponse;
	}
	
	public static ResponseModel newNoLogin(){
		ResponseModel filterResponse=new ResponseModel();
		filterResponse.setStatus(ResponseStatus.NO_LOGIN);
		return filterResponse;
	}
	
	public static ResponseModel newNoAccess(){
		ResponseModel filterResponse=new ResponseModel();
		filterResponse.setStatus(ResponseStatus.NO_ACCESS);
		return filterResponse;
	}
	
	public static ResponseModel newLinkedRequest(){
		ResponseModel filterResponse=new ResponseModel();
		filterResponse.setStatus(ResponseStatus.LINKED_REQUEST);
		return filterResponse;
	}
	
	public static ResponseModel newDuplicateLogin(){
		ResponseModel filterResponse=new ResponseModel();
		filterResponse.setStatus(ResponseStatus.DUPLICATE_LOGIN);
		return filterResponse;
	}
	
	public static ResponseModel newExpiredLogin(){
		ResponseModel filterResponse=new ResponseModel();
		filterResponse.setStatus(ResponseStatus.EXPIRED_LOGIN);
		return filterResponse;
	}
	
	public static ResponseModel newSuccessLogin(){
		ResponseModel filterResponse=new ResponseModel();
		filterResponse.setStatus(ResponseStatus.SUCCESS_LOGIN);
		return filterResponse;
	}
	
	public static ResponseModel newFormTokenInvalid(){
		ResponseModel filterResponse=new ResponseModel();
		filterResponse.setStatus(ResponseStatus.FORM_TOKEN_INVALID);
		return filterResponse;
	}
	
}
