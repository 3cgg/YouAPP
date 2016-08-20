package com.youappcorp.project;

public class BusinessException extends RuntimeException {

	public BusinessException(String message){
		super(message);
	}
	
	public BusinessException(Exception e){
		super(e);
	}
	
}
