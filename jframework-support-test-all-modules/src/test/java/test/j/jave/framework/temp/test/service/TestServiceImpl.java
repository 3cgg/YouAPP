package test.j.jave.framework.temp.test.service;

public class TestServiceImpl implements TestService {

	private volatile long index;
	
	@Override
	public void show() {
		System.out.println("show..."); 
	}
	
	@Override
	public Object trigger(TestServicePrintEvent event) {
		return "print "+this.getClass()+"::: "+(++index);
	}

}
