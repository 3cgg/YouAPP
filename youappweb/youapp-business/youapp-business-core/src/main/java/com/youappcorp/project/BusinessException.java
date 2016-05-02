package com.youappcorp.project;

public class BusinessException extends RuntimeException {

	public BusinessException(String message){
		super(message);
	}
	
	public BusinessException(Exception e){
		super(e);
	}
	
	public static void throwException(Exception e) throws BusinessException{
		if(BusinessException.class.isInstance(e)) throw (BusinessException)e;
		throw new BusinessException(e);
	}
	
	
}
