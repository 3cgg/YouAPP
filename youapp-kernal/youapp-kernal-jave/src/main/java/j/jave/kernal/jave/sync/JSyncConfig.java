package j.jave.kernal.jave.sync;

public class JSyncConfig {

	private int waitTime;

	public JSyncConfig() {
	}
	
	public JSyncConfig(int waitTime) {
		super();
		this.waitTime = waitTime;
	}


	/**
	 * in milliseconds
	 * @return
	 */
	public int getWaitTime() {
		return waitTime;
	}

	/**
	 * in milliseconds
	 * @param waitTime
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
	
}
