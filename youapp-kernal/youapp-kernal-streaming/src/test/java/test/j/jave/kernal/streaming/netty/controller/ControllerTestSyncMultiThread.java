package test.j.jave.kernal.streaming.netty.controller;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import me.bunny.kernel._c.utils.JDateUtils;
import me.bunny.kernel._c.utils.JUniqueUtils;
import me.bunny.modular._p.streaming.netty.client.SimpleInterfaceImplUtil;
import me.bunny.modular._p.streaming.netty.examples.IUnitController;

public class ControllerTestSyncMultiThread {

	private static StringBuffer stringBuffer=new StringBuffer(100000);
	
	private static volatile int max=0;
	
	public static void main(String[] args) {
		try{
			final IUnitController controller=SimpleInterfaceImplUtil.syncProxy(IUnitController.class);
			ExecutorService service=Executors.newFixedThreadPool(5);
			Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					try{
						System.out.println(max+"<>"+stringBuffer.substring(stringBuffer.length()-300, stringBuffer.length()));
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, 1, 10, TimeUnit.SECONDS);
			System.out.println("start:"+JDateUtils.format(new Date(),JDateUtils.yyyyMMddHHmmss));
			for(int i=0;i<1000000;i++){
				final int _i=i;
				service.execute(new Runnable() {
					@Override
					public void run() {
						try{
							if(max<_i){
								max=_i;
							}
							Object object1=controller.name(_i+"----"+JUniqueUtils.unique());
							stringBuffer.append("\r\n"+JDateUtils.formatWithSeconds(new Date())+"-----[call name]----  response----------"+object1);
							
							object1=controller.superName(_i+"----"+JUniqueUtils.unique());
							stringBuffer.append("\r\n"+JDateUtils.formatWithSeconds(new Date())+"-----[call superName]----response----------"+object1);
							
							object1=controller.jvm();
							stringBuffer.append("\r\n"+JDateUtils.formatWithSeconds(new Date())+"-----[call jvm]----response----------"+object1);
							
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			System.out.println("end:"+JDateUtils.format(new Date(),JDateUtils.yyyyMMddHHmmss));
			controller.hashCode();
//			System.out.println(controller);
		}catch(Exception e){
			e.printStackTrace();
//			LOGGER.error(" cannot proxy for class : "+ this.getServiceClass() + " ,"+getServiceImplClass());
			throw e;
		}
		
		
		
	}

}
