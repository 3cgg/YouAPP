package j.jave.platform.basicwebcomp.web.model;

public enum ResponseStatus {

	SUCCESS("SUCCESS"),
	FAIL("FAIL"),
	MESSAGE("MESSAGE"),
	NO_LOGIN("NO_LOGIN"),
	NO_ACCESS("NO_ACCESS"),
	INVALID_PATH("INVALID_RESOURCE"),
	LINKED_REQUEST("LINKED_REQUEST"),
	DUPLICATE_LOGIN("DUPLICATE_LOGIN");
	
	String name;
	
	int age=90;
	
	private ResponseStatus(String name) {
		this.name=name;
	}
}
