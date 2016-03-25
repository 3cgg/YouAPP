/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.action;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.reflect.JReflect;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicsupportcomp.core.context.SpringContextSupport;
import j.jave.platform.basicwebcomp.web.util.MappingMeta;
import j.jave.platform.basicwebcomp.web.util.MethodParamMeta;
import j.jave.platform.basicwebcomp.web.util.MethodParamObject;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.bind.HttpContextDataBinder;
import j.jave.platform.basicwebcomp.web.youappmvc.bind.HttpContextWithInnerProtocolDataBinder;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		String targetPath=httpContext.getTargetPath();
		
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("path processing : "+targetPath);
		}
		
		// resolve the path , then invoke the target method. 
	    // the path like /app/component/version/login.loginaction/toLogin
		String component =null;
		String mappingPath=null;
		String mutiPattern="^(/app/[a-zA-Z]+/[0-9]+){0,1}(/[a-zA-Z0-9._/]+)";
		Pattern pattern=Pattern.compile(mutiPattern);
		Matcher matcher=pattern.matcher(targetPath);
		if(matcher.matches()){
			component=getComponentVersion(matcher.group(1));
			mappingPath=matcher.group(2);
		}
		//resolve spring application , support multi-version of component.
		ApplicationContext applicationContext = null;
		if (JStringUtils.isNotNullOrEmpty(component)) {
			applicationContext = JComponentVersionSpringApplicationSupport.getApplicationContext(component);
		} else {
			applicationContext = SpringContextSupport.getApplicationContext();
		}
		
		MappingMeta mappingMeta=  mappingController.getMappingMeta(mappingPath);
		ControllerSupport object=(ControllerSupport) applicationContext.getBean(mappingMeta.getControllerName());
		Object navigate=null;
		try{
			// set HTTP context constructed above.
			object.setHttpContext(httpContext);
			
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
			object.removeHttpContext();
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
	
	private String getComponentVersion(String componentVer) {
		if(JStringUtils.isNullOrEmpty(componentVer)) return null;
		if(componentVer.startsWith("/")) componentVer=componentVer.replaceFirst("/", "");
		String[] targets=componentVer.split("/");
		String component;
		String appName=targets[0];
		String componentName=targets[1];
		int version=Integer.parseInt(targets[2]);
		component= JComponentVersionSpringApplicationSupport.unique(appName, componentName, version);
		return component;
	}
	
	private static final ControllerExecutor actionExecutor=new ControllerExecutor();
	
	private MappingController mappingController=new MappingController(JConfiguration.get());
	
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
