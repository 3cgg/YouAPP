package test.j.jave.kernal.streaming.netty.controller;

import java.lang.reflect.Method;

import j.jave.kernal.streaming.netty.client.SimpleControllerAsyncCall;
import j.jave.kernal.streaming.netty.client.SimpleInterfaceImplUtil;
import j.jave.kernal.streaming.netty.examples.IUnitController;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryManager;
import me.bunny.kernel.jave.utils.JUniqueUtils;

public class ControllerTestAsync {

	private static JServiceFactoryManager serviceFactoryManager=JServiceFactoryManager.get();
	
	public static void initialize() throws Exception {
		serviceFactoryManager.registerAllServices();
	}
	
	public static void main(String[] args) {
		try{
			initialize();
			IUnitController controller=SimpleInterfaceImplUtil.asyncProxy(IUnitController.class);
			for(int i=0;i<1000000;i++){
				final int _i=i;
				SimpleInterfaceImplUtil.async(controller.name(_i+"----"+JUniqueUtils.unique()))
				.addControllerAsyncCall(new SimpleControllerAsyncCall() {
					@Override
					public void success(Object proxy, Method method, Object[] args, Object returnVal) {
						System.out.println("---rd Thread["+Thread.currentThread().getName()
									+"]------response----------"+returnVal);
					}
				});
				SimpleInterfaceImplUtil.async(controller.superName(_i+"----"+JUniqueUtils.unique()))
				.addControllerAsyncCall(new SimpleControllerAsyncCall() {
					@Override
					public void success(Object proxy, Method method, Object[] args, Object returnVal) {
						System.out.println("---sup Thread["+Thread.currentThread().getName()
									+"]------response----------"+returnVal);
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
