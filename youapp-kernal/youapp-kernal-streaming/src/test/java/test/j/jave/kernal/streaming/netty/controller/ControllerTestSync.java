package test.j.jave.kernal.streaming.netty.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.netty.client.SimpleKryoIntarfaceImplUtil;
import j.jave.kernal.streaming.netty.test.IUnitController;

public class ControllerTestSync {

	public static void main(String[] args) {
		try{
			IUnitController controller=SimpleKryoIntarfaceImplUtil.syncProxy(IUnitController.class);
			ExecutorService service=Executors.newFixedThreadPool(1);
			for(int i=0;i<1000000;i++){
				final int _i=i;
				service.execute(new Runnable() {
					@Override
					public void run() {
						Object object1=controller.rd(_i+"----"+JUniqueUtils.unique());
						System.out.println("---------response----------"+object1);
					}
				});
			}
			controller.hashCode();
//			System.out.println(controller);
		}catch(Exception e){
			e.printStackTrace();
//			LOGGER.error(" cannot proxy for class : "+ this.getServiceClass() + " ,"+getServiceImplClass());
			throw e;
		}
		
		
		
	}

}
