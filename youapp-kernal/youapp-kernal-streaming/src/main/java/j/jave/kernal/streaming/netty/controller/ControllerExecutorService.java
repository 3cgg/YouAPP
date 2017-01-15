/**
 * 
 */
package j.jave.kernal.streaming.netty.controller;


import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import j.jave.kernal.streaming.netty.msg.RPCFullMessage;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.jave.reflect.JReflectionUtils;
import me.bunny.kernel.jave.service.JService;
import me.bunny.kernel.jave.utils.JDateUtils;

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
	
	public Object execute(RPCFullMessage messageMeta) throws Exception{

		String endpoint=messageMeta.uri();
		
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
	
//	MethodParamParser methodParamParser=new DefaultMethodParamParser();
	private Object[] resolveArgs(ControllerService controllerService,MappingMeta mappingMeta,RPCFullMessage messageMeta) throws Exception{
		return messageMeta.decoder().decode(mappingMeta);
	}
	 
}
