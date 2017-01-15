package me.bunny.app._c._web.web.model;

public enum ResponseStatus {

	SUCCESS("SUCCESS"),
	FAIL("FAIL"),
	MESSAGE("MESSAGE"),
	NO_LOGIN("NO_LOGIN"),
	SUCCESS_LOGIN("SUCCESS_LOGIN"),
	NO_ACCESS("NO_ACCESS"),
	EXPIRED_LOGIN("EXPIRED_LOGIN"),
	INVALID_PATH("INVALID_RESOURCE"),
	LINKED_REQUEST("LINKED_REQUEST"),
	DUPLICATE_LOGIN("DUPLICATE_LOGIN"),
	FORM_TOKEN_INVALID("FORM_TOKEN_INVALID");
	
	String name;
	
	int age=90;
	
	private ResponseStatus(String name) {
		this.name=name;
	}
}
