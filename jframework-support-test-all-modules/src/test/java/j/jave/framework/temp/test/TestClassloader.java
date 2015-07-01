/**
 * 
 */
package j.jave.framework.temp.test;

import j.jave.framework.commons.utils.JDateUtils;

import java.util.Date;

/**
 * @author J
 *
 */
public class TestClassloader {
	
	public static void main(String[] args) throws Exception {
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
		for(int i=0;i<10;i++){
			classLoader.loadClass("j.jave.framework.temp.test.TestClassloaderA");
			System.out.println(JDateUtils.formatWithSeconds(new Date())+", (before setting ) A value is "+TestClassloaderA.getA());
			TestClassloaderA.setA(i);
			System.out.println(JDateUtils.formatWithSeconds(new Date())+",  (after setting ) A value is "+TestClassloaderA.getA());
		}
		System.out.println(" Class.forName (below).... ");
		// split , another 
		for(int i=0;i<10;i++){
			Class.forName("j.jave.framework.temp.test.TestClassloaderA");
			System.out.println(JDateUtils.formatWithSeconds(new Date())+", (before setting ) A value is "+TestClassloaderA.getA());
			TestClassloaderA.setA(i);
			System.out.println(JDateUtils.formatWithSeconds(new Date())+",  (after setting ) A value is "+TestClassloaderA.getA());
		}
		
	}
	
	
}
