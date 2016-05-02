/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.controller;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JReflect;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.platform.basicsupportcomp.core.container.MappingMeta;
import j.jave.platform.basicsupportcomp.core.container.MethodParamMeta;
import j.jave.platform.basicwebcomp.web.util.MethodParamObject;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContextHolder;
import j.jave.platform.basicwebcomp.web.youappmvc.bind.HttpContextDataBinder;
import j.jave.platform.basicwebcomp.web.youappmvc.bind.HttpContextWithInnerProtocolDataBinder;

import org.apache.commons.lang3.time.StopWatch;

/**
 * to find target action , then execute method.  
 * <p>any HTTP context without request also can be executed. 
 * as so , you can test the action methods that <strong>never</strong> use the HttpServletRequest or HttpServletResponse
 * 
 * @author J
 */
public class ControllerExecutor implements JService {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(ControllerExecutor.class);
	
	public Object execute(HttpContext httpContext,MappingMeta mappingMeta
			,Object controllerObject) throws Exception{
		Object navigate=null;
		try{
			
			// promise the subsequence flow hold the http context.
			if(HttpContextHolder.get()==null){
				HttpContextHolder.set(httpContext);
			}
			
			StopWatch stopWatch=null;
			if(LOGGER.isDebugEnabled()){
				stopWatch=new StopWatch();
				stopWatch.start();
			}
			navigate=JReflect.invoke(controllerObject, mappingMeta.getMethodName(),
					mappingMeta.getMethodParamClasses(), resolveArgs(httpContext, mappingMeta)
					);
			if(LOGGER.isDebugEnabled()){
				try{
					LOGGER.debug("time of processing request in action is :"+JDateUtils.getTimeOffset(stopWatch.getTime()));
				}catch(Exception e){
					LOGGER.error("error to get time.");
				}
			}
		}finally{
			//no need release http context
			
		}
		return  navigate;
	}

	private Object[] resolveArgs(HttpContext httpContext,MappingMeta mappingMeta){
		boolean bindWithInnerProtocol=false;
		if(httpContext.getProtocol()!=null&&httpContext.getObjectTransModel()!=null){
			// data binding via inner protocol
			bindWithInnerProtocol=true;
		}
		
		MethodParamMeta[] methodParamMetas=  mappingMeta.getMethodParams();
		Object[] args=new Object[methodParamMetas.length];
		for(int i=0;i<methodParamMetas.length;i++){
			MethodParamObject methodParamObject=new MethodParamObject();
			methodParamObject.setMethodParamMeta(methodParamMetas[i]);
			if(bindWithInnerProtocol){
				new HttpContextWithInnerProtocolDataBinder(httpContext).bind(methodParamObject);
			}
			else{
				new HttpContextDataBinder(httpContext).bind(methodParamObject);
			}
			args[i]=methodParamObject.getObject();
		}
		return args;
	}
	
	private static final ControllerExecutor actionExecutor=new ControllerExecutor();
	
	private ControllerExecutor() {
	}
	
	public static ControllerExecutor newSingleExecutor(){
//		if(actionExecutor==null){
//			synchronized (ActionExecutor.class) {
//				actionExecutor=new ActionExecutor();
//			}
//		}
		return actionExecutor;
	}
}
