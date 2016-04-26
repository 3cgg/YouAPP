package j.jave.kernal.http;

public enum JHttpType {

	GET("GET"),POST("POST"),PUT("PUT"),DELETE("DELETE");
	
	private String value;
	
	private JHttpType(String value) {
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
}
