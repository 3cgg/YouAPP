package test.j.jave.framework.temp.test;

public class D {

	interface DA{}
	
	interface DB extends DA{}
	
	interface DC {}
	
	interface DE extends DB,DC{}
	
	public static void main(String[] args) {
		
		Class<?>[]  classes=DE.class.getInterfaces();
		System.out.println(classes);
	}
	
	
}
