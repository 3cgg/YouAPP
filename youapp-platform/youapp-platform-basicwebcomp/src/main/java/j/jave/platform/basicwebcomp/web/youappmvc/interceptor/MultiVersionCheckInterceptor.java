package j.jave.platform.basicwebcomp.web.youappmvc.interceptor;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicsupportcomp.core.context.SpringContextSupport;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.utils.YouAppMvcUtils;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * process path with multi-version.
 * such as /youappcomp/appname/component/version/billcontroller/getAllBills
 * @author JIAZJ
 *
 */
public class MultiVersionCheckInterceptor implements ServletRequestInterceptor {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(MultiVersionCheckInterceptor.class);
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {
		HttpServletRequest req=servletRequestInvocation.getHttpServletRequest();
		try{
			String targetPath=YouAppMvcUtils.getPathInfo(req);
            String unique =SpringContextSupport.PLATFORM;
            String mappingPath=null;
            String mutiPattern="^(/youappcomp/[a-zA-Z]+/[a-zA-Z]+/[0-9]+){0,1}(/[a-zA-Z0-9._/]+)";
            Pattern pattern=Pattern.compile(mutiPattern);
            Matcher matcher=pattern.matcher(targetPath);
            if(matcher.matches()){
            	String tempUnique=getUnique(matcher.group(1));
            	if(JStringUtils.isNotNullOrEmpty(tempUnique)){
            		unique=tempUnique;
            	}
                mappingPath=matcher.group(2);
            }
            servletRequestInvocation.setUnique(unique);
            servletRequestInvocation.setMappingPath(mappingPath);
            
            return servletRequestInvocation.proceed();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return ResponseModel.newError().setData(e.getMessage());
		}
		
	}
	
	private String getUnique(String componentVer) {
		if (JStringUtils.isNullOrEmpty(componentVer))
			return null;
		if (componentVer.startsWith("/"))
			componentVer = componentVer.replaceFirst("/", "");
		String[] targets = componentVer.split("/");
		String unique;
		String appName = targets[1];
		String componentName = targets[2];
		int version = Integer.parseInt(targets[3]);
		unique = JComponentVersionSpringApplicationSupport.unique(appName,
				componentName, version);
		return unique;
	}

	
}
