package j.jave.kernal.container.scheme;

public enum JScheme {

	//for inner spring bean 
	BEAN("bean"),
	// for inner controller service of web application
	CONTROLLER("controller"),
	//delegate to request service from remote host
	REMOTE_HTTP("rhttp"),
	//always get resource from class path
	CLASSPATH("classpath"),
	//always get resource from local file system
	FILE("file"),
	//always get resource from http protocol
	HTTP("http"),
	HTTPS("https"),
	//always get resource from ftp protocol
	FTP("ftp"),
	JAR("jar");
	
	private final String value;
	
	private JScheme(String value) {
		this.value=value;
	}
	public String getValue() {
		return value;
	}
}
