package j.jave.platform.basicwebcomp.web.youappmvc.jsonview;

public class JSONResult {
	
	/**
	 * return status.  OK! or MESSAGE! or ERROR! 
	 * 1 means OK , 0 means MESSAGE, -1 means ERROR. 
	 */
	private String status;
	
	/**
	 * the data for the subview if status is OK , message for showing if status is ERROR.
	 */
	private Object data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public static JSONResult newSuccess(){
		JSONResult mobileResult=new JSONResult();
		mobileResult.status="1";
		return mobileResult;
	}

	public static JSONResult newMessage(){
		JSONResult mobileResult=new JSONResult();
		mobileResult.status="0";
		return mobileResult;
	}
	
	public static JSONResult newError(){
		JSONResult mobileResult=new JSONResult();
		mobileResult.status="-1";
		return mobileResult;
	}
	
}
