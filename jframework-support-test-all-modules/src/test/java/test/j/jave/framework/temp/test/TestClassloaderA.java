/**
 * 
 */
package test.j.jave.framework.temp.test;

import j.jave.framework.commons.utils.JDateUtils;

import java.util.Date;

/**
 * @author J
 */
public class TestClassloaderA {

	private static int a=1;
	
	static{
		a=a++;
		System.out.println("execute static chuck on the "+JDateUtils.formatWithSeconds(new Date()));
	}
	
	static int getA(){
		return a;
	}
	
	static void setA(int a){
		TestClassloaderA.a=a;
	}
	
}
