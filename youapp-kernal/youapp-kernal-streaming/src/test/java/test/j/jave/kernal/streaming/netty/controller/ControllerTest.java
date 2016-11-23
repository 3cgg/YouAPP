package test.j.jave.kernal.streaming.netty.controller;

import j.jave.kernal.streaming.netty.client.SimpleKryoIntarfaceImplUtil;
import j.jave.kernal.streaming.netty.test.IUnitController;

public class ControllerTest {

	public static void main(String[] args) {
		try{
			IUnitController controller=SimpleKryoIntarfaceImplUtil.syncProxy(IUnitController.class);
			
			for(int i=0;i<1000000;i++){
				Object object1=controller.rd("aaa-"+i);
				System.out.println("---------response----------"+object1);
			}
			controller.hashCode();
			System.out.println(controller);
		}catch(Exception e){
			e.printStackTrace();
//			LOGGER.error(" cannot proxy for class : "+ this.getServiceClass() + " ,"+getServiceImplClass());
			throw e;
		}
		
		
		
	}

}
