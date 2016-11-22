/**
 * 
 */
package j.jave.kernal.streaming.netty.controller;


import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import j.jave.kernal.dataexchange.model.MessageMeta;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.reflect.JReflectionUtils;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JDateUtils;

/**
 * 
 * @author J
 */
public class ControllerExecutorService 
extends JServiceFactorySupport<ControllerExecutorService>
implements JService {
	
	@Override
	protected ControllerExecutorService doGetService() {
		return actionExecutor;
	}
	
	private static final ControllerExecutorService actionExecutor=new ControllerExecutorService();
	
	public ControllerExecutorService() {
	}
	
	public static ControllerExecutorService newSingleExecutor(){
		return actionExecutor;
	}
	
	private static final MappingControllerManager mappingControllerManager =MappingControllerManager.get();
	
	public Object execute(MessageMeta messageMeta) throws Exception{

		String endpoint=messageMeta.url();
		
		MappingMeta mappingMeta= mappingControllerManager.getMappingMeta(endpoint);
		
		ControllerService controllerService= mappingMeta.getControllerServiceFactory().getControllerService();
		
		Object[] argums=resolveArgs(controllerService, mappingMeta, messageMeta);
		
		Object navigate=null;
		try{
			
			Stopwatch stopWatch=null;
			if(LOGGER.isDebugEnabled()){
				stopWatch=Stopwatch.createUnstarted();
				stopWatch.start();
			}
			navigate=JReflectionUtils.invoke(controllerService, mappingMeta.getMethodName(),
					mappingMeta.getMethodParamClasses(), argums);
			if(LOGGER.isDebugEnabled()){
				try{
					long time=stopWatch.elapsed(TimeUnit.MILLISECONDS);
					LOGGER.debug("time of processing request in controller is :"+time
					+" - "+JDateUtils.getTimeOffset(time));
				}catch(Exception e){
					LOGGER.error("error to get time.");
				}
			}
		}finally{
			
		}
		return  navigate;
		
	}
	
	MethodParamParser methodParamParser=new DefaultMethodParamParser();
	private Object[] resolveArgs(ControllerService controllerService,MappingMeta mappingMeta,MessageMeta messageMeta) throws Exception{
		return methodParamParser.parse(controllerService, mappingMeta,  (FastMessageMeta) messageMeta);
	}
	 
}
