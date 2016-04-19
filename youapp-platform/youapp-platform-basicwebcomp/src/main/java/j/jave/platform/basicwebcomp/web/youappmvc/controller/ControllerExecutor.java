/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.controller;

import j.jave.kernal.jave.reflect.JReflect;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicsupportcomp.core.context.SpringContextSupport;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.util.MappingMeta;
import j.jave.platform.basicwebcomp.web.util.MethodParamMeta;
import j.jave.platform.basicwebcomp.web.util.MethodParamObject;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContextHolder;
import j.jave.platform.basicwebcomp.web.youappmvc.bind.HttpContextDataBinder;
import j.jave.platform.basicwebcomp.web.youappmvc.bind.HttpContextWithInnerProtocolDataBinder;
import j.jave.platform.basicwebcomp.web.youappmvc.controller.MappingControllerManagers.MappingControllerManager;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport.Component;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * to find target action , then execute method.  
 * <p>any HTTP context without request also can be executed. 
 * as so , you can test the action methods that <strong>never</strong> use the HttpServletRequest or HttpServletResponse
 * 
 * @author J
 */
public class ControllerExecutor implements JService {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(ControllerExecutor.class);
	
	public Object execute(HttpContext httpContext) throws Exception{
		Object navigate=null;
		try{
			
			// promise the subsequence flow hold the http context.
			if(HttpContextHolder.get()==null){
				HttpContextHolder.set(httpContext);
			}
			
			String targetPath=httpContext.getTargetPath();
			
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("processing path : "+targetPath);
			}
			
			// resolve the path , then invoke the target method. 
		    // the path like /app/component/version/login.loginaction/toLogin
			
//			String appName =httpContext.getParameter(Component.YOUAPP_WEB_APP_NAME_KEY);
			String component=httpContext.getUnique();
//			if(JStringUtils.isNotNullOrEmpty(appName)){
//				String comName =httpContext.getParameter(Component.YOUAPP_WEB_COM_NAME_KEY);
//				String comVer =httpContext.getParameter(Component.YOUAPP_WEB_COM_VER_KEY);
//				component=JComponentVersionSpringApplicationSupport.unique(appName, comName, comVer);
//			}

			//resolve spring application , support multi-version of component.
			ApplicationContext applicationContext = null;
			MappingControllerManager mappingControllerManager=null;
			if (JStringUtils.isNotNullOrEmpty(component)) {
				applicationContext = JComponentVersionSpringApplicationSupport.getApplicationContext(component);
				mappingControllerManager=MappingControllerManagers.getMappingControllerManager(component);
			} else {
				applicationContext = SpringContextSupport.getApplicationContext();
				mappingControllerManager=MappingControllerManagers.getMappingControllerManager(MappingControllerManagers.PLATFORM);
			}
			
			MappingMeta mappingMeta= null;
			if(mappingControllerManager==null
					||(mappingMeta=mappingControllerManager.getMappingMeta(targetPath))==null){
				return ResponseModel.newError().setData("cannot find any controller for the path. "
						+ " check if turn on multiple component version infrastructure (immutable version)."
						+ " attempt to prefix /youappcomp/[appname]/[component]/[compversion]/...  you actual path.");
			}
			ControllerSupport object=null;
			String controllerName=mappingMeta.getControllerName();
			if(JStringUtils.isNotNullOrEmpty(controllerName)){
				object=(ControllerSupport) applicationContext.getBean(mappingMeta.getControllerName());
			}
			
			if(object==null){
				return ResponseModel.newError().setData("cannot find any controller for the path. "
						+ " check if turn on multiple component version infrastructure (immutable version)."
						+ " attempt to prefix /youappcomp/[appname]/[component]/[compversion]/...  you actual path.");
			}
			
			StopWatch stopWatch=null;
			if(LOGGER.isDebugEnabled()){
				stopWatch=new StopWatch();
				stopWatch.start();
			}
			navigate=JReflect.invoke(object, mappingMeta.getMethodName(),
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
