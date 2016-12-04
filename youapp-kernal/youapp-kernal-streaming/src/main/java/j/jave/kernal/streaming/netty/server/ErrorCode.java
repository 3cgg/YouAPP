package j.jave.kernal.streaming.netty.server;

public enum ErrorCode {

	E0001("E0001");
	
	private final String name;
	
	private ErrorCode(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
}
