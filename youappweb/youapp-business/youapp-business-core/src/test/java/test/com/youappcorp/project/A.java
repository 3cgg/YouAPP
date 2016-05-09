package test.com.youappcorp.project;

import junit.textui.TestRunner;

public class A {

	public static void main(String[] args) {
		TestRunner.main(new String[]{"-c","test.com.youappcorp.project.TestAnything","-m","test.com.youappcorp.project.TestAnything.testEnum"});
		
	}
}
