package test.j.jave.kernal.streaming.netty.controller;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.netty.client.ControllerAsyncCall;
import j.jave.kernal.streaming.netty.client.SimpleIntarfaceImplUtil;
import j.jave.kernal.streaming.netty.examples.IUnitController;

public class ControllerTestAsync {

	private static JServiceFactoryManager serviceFactoryManager=JServiceFactoryManager.get();
	
	public static void initialize() throws Exception {
		serviceFactoryManager.registerAllServices();
	}
	
	public static void main(String[] args) {
		try{
			initialize();
			IUnitController controller=SimpleIntarfaceImplUtil.asyncProxy(IUnitController.class);
			for(int i=0;i<1000000;i++){
				final int _i=i;
				SimpleIntarfaceImplUtil.asyncExecute(controller.rd(_i+"----"+JUniqueUtils.unique())
						,new ControllerAsyncCall() {
							@Override
							public void run(Object object) {
								System.out.println("---rd Thread["+Thread.currentThread().getName()+"]------response----------"+object);
							}
						});
				controller.sup(_i+"----"+JUniqueUtils.unique());
				SimpleIntarfaceImplUtil.asyncExecute(null
						,new ControllerAsyncCall() {
							@Override
							public void run(Object object) {
								System.out.println("---sup Thread["+Thread.currentThread().getName()+"]------response----------"+object);
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
