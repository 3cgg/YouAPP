package test.j.jave.kernal.streaming.netty.controller;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.netty.client.ControllerAsyncCall;
import j.jave.kernal.streaming.netty.client.SimpleKryoIntarfaceImplUtil;
import j.jave.kernal.streaming.netty.test.IUnitController;

public class ControllerTestAsync {

	private static JServiceFactoryManager serviceFactoryManager=JServiceFactoryManager.get();
	
	public static void initialize() throws Exception {
		serviceFactoryManager.registerAllServices();
	}
	
	public static void main(String[] args) {
		try{
			initialize();
			IUnitController controller=SimpleKryoIntarfaceImplUtil.asyncProxy(IUnitController.class);
			for(int i=0;i<1000000;i++){
				final int _i=i;
				SimpleKryoIntarfaceImplUtil.asyncExecute(controller.rd(_i+"----"+JUniqueUtils.unique())
						,new ControllerAsyncCall() {
							@Override
							public void run(Object object) {
								System.out.println("---Thread["+Thread.currentThread().getName()+"]------response----------"+object);
							}
						});
				
			}
			controller.hashCode();
//			System.out.println(controller);
		}catch(Exception e){
			e.printStackTrace();
//			LOGGER.error(" cannot proxy for class : "+ this.getServiceClass() + " ,"+getServiceImplClass());
		}
		
		
		
	}

}
