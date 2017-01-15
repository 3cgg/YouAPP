package j.jave.platform.streaming.classcount;

import me.bunny.kernel._c.model.JModel;

public class RecordInfo implements JModel{
	private String className;

	private String logLevel;
	
	private String logTime;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
}
