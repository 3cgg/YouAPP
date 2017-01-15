/**
 * 
 */
package j.jave.platform.webcomp.web.youappmvc.controller;

import j.jave.platform.data.common.MethodParamMeta;
import j.jave.platform.data.common.MethodParamObject;
import j.jave.platform.data.web.mapping.MappingMeta;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.HttpContextHolder;
import j.jave.platform.webcomp.web.youappmvc.bind.HttpContextDataBinder;
import j.jave.platform.webcomp.web.youappmvc.bind.HttpContextWithInnerProtocolDataBinderAdapter;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.reflect.JReflectionUtils;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel._c.utils.JDateUtils;

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
			navigate=JReflectionUtils.invoke(controllerObject, mappingMeta.getMethodName(),
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
				new HttpContextWithInnerProtocolDataBinderAdapter(httpContext).bind(methodParamObject);
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
