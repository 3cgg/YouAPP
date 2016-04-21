package j.jave.kernal.container;

public enum Scheme {

	BEAN("bean"),CONTROLLER("controller"),EXECUTE("execute");
	
	private final String value;
	
	private Scheme(String value) {
		this.value=value;
	}
	public String getValue() {
		return value;
	}
}
