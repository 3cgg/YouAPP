/**
 * 
 */
package j.jave.platform.standalone.server.controller;


import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.reflect.JReflect;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.platform.standalone.data.MessageMeta;

import org.apache.commons.lang3.time.StopWatch;

/**
 * 
 * @author J
 */
public class ControllerExecutor 
extends JServiceFactorySupport<ControllerExecutor>
implements JService {
	
	@Override
	public ControllerExecutor getService() {
		return actionExecutor;
	}
	
	private static final ControllerExecutor actionExecutor=new ControllerExecutor();
	
	public ControllerExecutor() {
	}
	
	public static ControllerExecutor newSingleExecutor(){
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
			
			StopWatch stopWatch=null;
			if(LOGGER.isDebugEnabled()){
				stopWatch=new StopWatch();
				stopWatch.start();
			}
			navigate=JReflect.invoke(controllerService, mappingMeta.getMethodName(),
					mappingMeta.getMethodParamClasses(), argums);
			if(LOGGER.isDebugEnabled()){
				try{
					LOGGER.debug("time of processing request in action is :"+JDateUtils.getTimeOffset(stopWatch.getTime()));
				}catch(Exception e){
					LOGGER.error("error to get time.");
				}
			}
		}finally{
			
		}
		return  navigate;
		
	}
	
	
	private Object[] resolveArgs(ControllerService controllerService,MappingMeta mappingMeta,MessageMeta messageMeta){
		
		return null;
	}
	 
}
