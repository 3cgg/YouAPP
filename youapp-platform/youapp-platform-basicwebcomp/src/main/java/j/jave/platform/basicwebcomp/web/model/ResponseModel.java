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
}
