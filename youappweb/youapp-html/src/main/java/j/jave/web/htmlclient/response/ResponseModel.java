package j.jave.web.htmlclient.response;

import j.jave.kernal.jave.model.JModel;
import j.jave.web.htmlclient.form.FormIdentification;


@SuppressWarnings("serial")
public class ResponseModel implements JModel{
	
	/**
	 * return status.  OK! or MESSAGE! or ERROR! 
	 * 1 means OK , 0 means MESSAGE, -1 means ERROR. 
	 * etc....
	 */
	private String status;
	
	/**
	 * the data for the subview if status is OK , message for showing if status is ERROR.
	 */
	private Object data;
	
	/**
	 * the refreshed token/form identification.
	 */
	private FormIdentification token;

	public FormIdentification getToken() {
		return token;
	}

	public ResponseModel setToken(FormIdentification token) {
		this.token = token;
		return this;
	}

	public Object getData() {
		return data;
	}

	public ResponseModel setData(Object data) {
		this.data = data;
		return this;
	}
	
	public static ResponseModel newSuccess(){
		ResponseModel mobileResult=new ResponseModel();
		mobileResult.status="1";
		return mobileResult;
	}

	public static ResponseModel newMessage(){
		ResponseModel mobileResult=new ResponseModel();
		mobileResult.status="0";
		return mobileResult;
	}
	
	public static ResponseModel newError(){
		ResponseModel mobileResult=new ResponseModel();
		mobileResult.status="-1";
		return mobileResult;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isSuccess(){
		return "1".equals(status)||"SUCCESS".equals(status)||"SUCCESS_LOGIN".equals(status);
	}
	
}
