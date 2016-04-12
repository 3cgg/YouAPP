package test.j.jave.kernal.datapropertyvalidate;

import j.jave.kernal.jave.support.validate.propertyvalidator.JSimplePropertyAnnoationValidator;

public class TestModelMain {

	public static void main(String[] args) {
		
		JSimplePropertyAnnoationValidator<TestModelForValidating>
		
		simplePropertyAnnoationValidator=new JSimplePropertyAnnoationValidator<TestModelForValidating>();
		
		boolean passed=simplePropertyAnnoationValidator.validate(new TestModelForValidating());
		System.out.println("PASSED: ->"+passed);
		
	}
	
}
