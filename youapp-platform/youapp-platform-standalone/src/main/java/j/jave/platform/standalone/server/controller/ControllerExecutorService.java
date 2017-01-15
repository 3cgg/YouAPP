/**
 * 
 */
package j.jave.platform.standalone.server.controller;


import org.apache.commons.lang3.time.StopWatch;

import me.bunny.kernel._c.reflect.JReflectionUtils;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel._c.utils.JDateUtils;
import me.bunny.kernel.dataexchange.model.MessageMeta;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

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
			
			StopWatch stopWatch=null;
			if(LOGGER.isDebugEnabled()){
				stopWatch=new StopWatch();
				stopWatch.start();
			}
			navigate=JReflectionUtils.invoke(controllerService, mappingMeta.getMethodName(),
					mappingMeta.getMethodParamClasses(), argums);
			if(LOGGER.isDebugEnabled()){
				try{
					LOGGER.debug("time of processing request in controller is :"+stopWatch.getTime()+" - "+JDateUtils.getTimeOffset(stopWatch.getTime()));
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
		return methodParamParser.parse(controllerService, mappingMeta,  messageMeta.data());
	}
	 
}
