package test.j.jave.framework.temp.test;

public class F {

	static class FA{
		
		private FB b=new FB();
	}
	
	static class FB{
		private FA a=new FA();
	}
	
	
	public static void main(String[] args) {
		FA A=new FA();
		
		System.out.println(A);
		
	}
	
}
