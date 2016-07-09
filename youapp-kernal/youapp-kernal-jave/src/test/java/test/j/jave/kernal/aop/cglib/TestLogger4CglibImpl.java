package test.j.jave.kernal.aop.cglib;

public class TestLogger4CglibImpl {
	
	public void log(String msg){
		System.out.println("logging----------------->"+msg);
	}
	
	public static void show(String name){
		System.out.println("-----------static  show-----("+TestLogger4CglibImpl.class.getSimpleName()+")-- ");
	}
	
	@Override
	public boolean equals(Object obj) {
		System.out.println("---------equals-------------");
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		System.out.println("-------hachCode----------");
		return super.hashCode();
	}
	
}
