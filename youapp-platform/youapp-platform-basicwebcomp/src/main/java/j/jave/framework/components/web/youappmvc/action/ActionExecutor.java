/**
 * 
 */
package j.jave.framework.components.web.youappmvc.action;

import j.jave.framework.commons.reflect.JReflect;
import j.jave.framework.commons.utils.JDateUtils;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.components.core.context.SpringContextSupport;
import j.jave.framework.components.multi.version.JComponentVersionSpringApplicationSupport;
import j.jave.framework.components.web.youappmvc.model.JHttpContext;
import j.jave.framework.components.web.youappmvc.utils.JHttpUtils;

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
public class ActionExecutor {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(ActionExecutor.class);
	
	public Object execute(JHttpContext httpContext) throws Exception{
		
		String targetPath=httpContext.getTargetPath();
		
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("path processing : "+targetPath);
		}
		
		// resolve the path , then invoke the target method. 
	    // the path like /app/component/version/login.loginaction/toLogin
		
		String serviceName="";;
		String methodName="";
		if(targetPath.startsWith("/")) targetPath=targetPath.substring(1);
		String[] targets=targetPath.split("/");
		String component =null;
		//compatible with previous
		if(targets.length==2){
			serviceName=targets[0];
			methodName=targets[1];
		}
		// for multi-version of component
		else if(targets.length==5){
			String appName=targets[0];
			String componentName=targets[1];
			int version=Integer.parseInt(targets[2]);
			component= JComponentVersionSpringApplicationSupport.unique(appName, componentName, version);
			serviceName=targets[3];
			methodName=targets[4];
		}
		//resolve spring application , support multi-version of component.
		ApplicationContext applicationContext = null;
		if (JStringUtils.isNotNullOrEmpty(component)) {
			applicationContext = JComponentVersionSpringApplicationSupport.getApplicationContext(component);
		} else {
			applicationContext = SpringContextSupport.getApplicationContext();
		}
		
		AbstractAction object=(AbstractAction) applicationContext.getBean(serviceName);
		
		// set HTTP context constructed above.
		object.setHttpContext(httpContext);
		
		//setting attributes associate to the request. 
		JHttpUtils.set(object, httpContext);
		
		StopWatch stopWatch=null;
		if(LOGGER.isDebugEnabled()){
			stopWatch=new StopWatch();
			stopWatch.start();
		}
		Object navigate=JReflect.invoke(object, methodName, new Object[]{});
		if(LOGGER.isDebugEnabled()){
			try{
				LOGGER.debug("time of processing request in action is :"+JDateUtils.getTimeOffset(stopWatch.getTime()));
			}catch(Exception e){
				LOGGER.error("error to get time.");
			}
		}
		return  navigate;
	}
	
	private static ActionExecutor actionExecutor;
	
	private ActionExecutor() {
	}
	
	public static ActionExecutor newSingleExecutor(){
		if(actionExecutor==null){
			synchronized (ActionExecutor.class) {
				actionExecutor=new ActionExecutor();
			}
		}
		return actionExecutor;
	}
}
