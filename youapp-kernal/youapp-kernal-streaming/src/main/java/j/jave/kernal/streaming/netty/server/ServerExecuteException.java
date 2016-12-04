package j.jave.kernal.streaming.netty.server;

import j.jave.kernal.jave.model.JModel;

public class ServerExecuteException implements JModel{
	
	public interface ErrorCode{
		String E0001="E0001";
	}
	
	private String code;
	
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
