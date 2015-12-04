package test.j.jave.framework.temp.test;

import j.jave.framework.commons.eventdriven.servicehub.JServiceFactoryManager;

public class I {

	
	public static void main(String[] args) {
		
		JServiceFactoryManager.get().registerAllServices();
		
		System.out.println("end");
		
	}
}
