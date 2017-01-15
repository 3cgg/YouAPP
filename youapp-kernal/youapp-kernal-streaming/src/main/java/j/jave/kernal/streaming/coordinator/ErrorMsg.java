package j.jave.kernal.streaming.coordinator;

import me.bunny.kernel.jave.model.JModel;

public class ErrorMsg implements JModel {
	
	private String msg;

	private String hostAddress;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}
	
}
