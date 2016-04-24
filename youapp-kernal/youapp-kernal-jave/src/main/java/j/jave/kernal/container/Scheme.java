package j.jave.kernal.container;

public enum Scheme {

	BEAN("bean"),CONTROLLER("controller"),REMOTE_HTTP("rhttp");
	
	private final String value;
	
	private Scheme(String value) {
		this.value=value;
	}
	public String getValue() {
		return value;
	}
}
