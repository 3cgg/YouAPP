package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.platform.sps.core.context.SpringContextSupport;
import j.jave.platform.sps.multiv.ComponentVersionSpringApplicationSupport;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.VerMappingMeta;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JStringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		try{
			String targetPath=servletRequestInvocation.getHttpContext().getRequestPath();
            String unique =SpringContextSupport.PLATFORM;
            String mappingPath=null;
            String mark="/youappcomp/";
            int index=targetPath.indexOf(mark);
            if(index!=-1){
            	targetPath=targetPath.substring(index,targetPath.length());
            	String mutiPattern="^(/youappcomp/[a-zA-Z-]+/[a-zA-Z-]+/[0-9.]+){0,1}(/[a-zA-Z0-9._/]+)";
                Pattern pattern=Pattern.compile(mutiPattern);
                Matcher matcher=pattern.matcher(targetPath);
                if(matcher.matches()){
                	String tempUnique=getUnique(matcher.group(1));
                	if(JStringUtils.isNotNullOrEmpty(tempUnique)){
                		unique=tempUnique;
                	}
                    mappingPath=matcher.group(2);
                }
            }
            else{
            	mappingPath=targetPath;
            }
            VerMappingMeta verMappingMeta=new VerMappingMeta();
            verMappingMeta.setUnique(unique);
            verMappingMeta.setMappingPath(mappingPath);
            servletRequestInvocation.getHttpContext().setVerMappingMeta(verMappingMeta);
            return servletRequestInvocation.proceed();
		}catch(Throwable e){
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
		String version = targets[3];
		unique = ComponentVersionSpringApplicationSupport.unique(appName,
				componentName, version);
		return unique;
	}

	
}
