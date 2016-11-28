package test.j.jave.kernal.streaming.netty.controller;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.netty.client.SimpleIntarfaceImplUtil;
import j.jave.kernal.streaming.netty.examples.IUnitController;

public class ControllerTestSyncSingleThread {

	private static StringBuffer stringBuffer=new StringBuffer(100000);
	
	
	public static void main(String[] args) {
		try{
			
			Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					try{
						System.out.println(stringBuffer.substring(stringBuffer.length()-300, stringBuffer.length()));
					}catch (Exception e) {
					}
				}
			}, 1, 10, TimeUnit.SECONDS);
			
			IUnitController controller=SimpleIntarfaceImplUtil.syncProxy(IUnitController.class);
			System.out.println(JDateUtils.format(new Date(),JDateUtils.yyyyMMddHHmmss));
			for(int i=0;i<1000000;i++){
				final int _i=i;
				Object object1=controller.name(_i+"----"+JUniqueUtils.unique());
				stringBuffer.append("\r\n-----[call name]----  response----------"+object1);
				
				object1=controller.superName(_i+"----"+JUniqueUtils.unique());
				stringBuffer.append("\r\n-----[call superName]----response----------"+object1);
				
				object1=controller.jvm();
				stringBuffer.append("\r\n-----[call jvm]----response----------"+object1);
				
			}
			System.out.println(JDateUtils.format(new Date(),JDateUtils.yyyyMMddHHmmss));
			controller.hashCode();
//			System.out.println(controller);
		}catch(Exception e){
			e.printStackTrace();
//			LOGGER.error(" cannot proxy for class : "+ this.getServiceClass() + " ,"+getServiceImplClass());
			throw e;
		}
		
		
		
	}

}
