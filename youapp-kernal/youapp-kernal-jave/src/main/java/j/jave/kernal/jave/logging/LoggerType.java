package j.jave.kernal.jave.logging;

public enum LoggerType {
	
	DEBUG("DEBUG"),
	INFO("INFO"),
	WARN("WARN"),
	ERROR("ERROR"),
	TRACE("TRACE"),
	FATAL("FATAL");
	
	
	private final String name;

	private LoggerType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	

}
