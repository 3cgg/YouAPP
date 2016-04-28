package j.jave.kernal.container;

public enum JScheme {

	BEAN("bean"),CONTROLLER("controller"),REMOTE_HTTP("rhttp");
	
	private final String value;
	
	private JScheme(String value) {
		this.value=value;
	}
	public String getValue() {
		return value;
	}
}
