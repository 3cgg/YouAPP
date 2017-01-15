package test.j.jave.kernal.streaming.netty.controller;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import j.jave.kernal.streaming.netty.client.SimpleInterfaceImplUtil;
import j.jave.kernal.streaming.netty.examples.IUnitController;
import me.bunny.kernel.jave.utils.JDateUtils;
import me.bunny.kernel.jave.utils.JUniqueUtils;

public class ControllerTestSyncSingleThread {

	private static StringBuffer stringBuffer=new StringBuffer(100000);
	
	private static int count=0;
	
	public static void main(String[] args) {
		try{
			
			Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					try{
						System.out.println("count["+count+"];"+stringBuffer.substring(stringBuffer.length()-300, stringBuffer.length()));
					}catch (Exception e) {
					}
				}
			}, 1, 10, TimeUnit.SECONDS);
			
			IUnitController controller=SimpleInterfaceImplUtil.syncProxy(IUnitController.class);
			System.out.println(JDateUtils.format(new Date(),JDateUtils.yyyyMMddHHmmss));
			for(int i=0;i<1000000;i++){
				final int _i=i;
				Object object1=null;
				if(_i==1){
					try{
						object1=controller.name("exception");
					}catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					object1=controller.name(_i+"----"+JUniqueUtils.unique());
				}
				
				stringBuffer.append("\r\n-----[call name]----  response----------"+object1);
				count++;
//				object1=controller.superName(_i+"----"+JUniqueUtils.unique());
//				stringBuffer.append("\r\n-----[call superName]----response----------"+object1);
//				
//				object1=controller.jvm();
//				stringBuffer.append("\r\n-----[call jvm]----response----------"+object1);
				
			}
			System.out.println(JDateUtils.format(new Date(),JDateUtils.yyyyMMddHHmmss));
			controller.hashCode();
//			System.out.println(controller);
		}catch(Exception e){
//			LOGGER.error(" cannot proxy for class : "+ this.getServiceClass() + " ,"+getServiceImplClass());
			throw e;
		}
		
		
		
	}

}
