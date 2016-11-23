package test.j.jave.kernal.streaming.netty.controller;

import j.jave.kernal.streaming.netty.client.KryoChannelExecutor;
import j.jave.kernal.streaming.netty.client.KryoIntarfaceImpl;
import j.jave.kernal.streaming.netty.test.IUnitController;

public class ControllerTest {

	public static void main(String[] args) {
		try{
			KryoChannelExecutor channelExecutor=new KryoChannelExecutor("127.0.0.1", 8080);
			
			KryoIntarfaceImpl<IUnitController> intarface=
					new KryoIntarfaceImpl<>(IUnitController.class, channelExecutor);
			
			IUnitController controller=intarface.syncProxy();
			Object object1=controller.rd("aaa");
			controller.hashCode();
			System.out.println(controller);
		}catch(Exception e){
			e.printStackTrace();
//			LOGGER.error(" cannot proxy for class : "+ this.getServiceClass() + " ,"+getServiceImplClass());
			throw e;
		}
		
		
		
	}

}
