package j.jave.platform.basicsupportcomp.core.container;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.Scheme;
import j.jave.platform.multiversioncompsupportcomp.ComponentVersionApplication;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;

public class JSpringCompRunner implements JRunner {

	private String unique;
	
	private String name;
	
	private final ApplicationContext applicationContext;
	
	private final ComponentVersionApplication componentVersionApplication;
	
	private final SpringCompMicroContainerConfig springCompMicroContainerConfig;
	
	public JSpringCompRunner(ApplicationContext applicationContext,ComponentVersionApplication componentVersionApplication,SpringCompMicroContainerConfig springCompMicroContainerConfig) {
		this.applicationContext=applicationContext;
		this.componentVersionApplication=componentVersionApplication;
		this.springCompMicroContainerConfig=springCompMicroContainerConfig;
	}
	
	@Override
	public String unique() {
		return unique;
	}
	
	@Override
	public String name() {
		return name;
	}
	
	public ComponentVersionApplication getComponentVersionApplication() {
		return componentVersionApplication;
	}
	
	public static enum Type{
		
		GET("get"),PUT("put"),DELETE("delete");
		
		private String value;
		
		Type(String value){
			this.value=value;
		}
		public String getValue() {
			return value;
		}
	}

	private static final String UNIQUE="unique";
	
	private static final String BEAN_NAME="beanName";
	
	/**
	 * controller://get?unique=%s&beanName=%s
	 */
	private static final String URI=Scheme.BEAN.getValue()+"://%s?"+UNIQUE+"=%s&"+BEAN_NAME+"=%s";
	
	/**
	 * ^unique=([a-zA-Z:0-9_]+)&beanName=([a-zA-Z:0-9_]+)$
	 */
	private static final String REGX="^"+UNIQUE+"=([a-zA-Z:0-9_]+)&"+BEAN_NAME+"=([a-zA-Z:0-9_]+)$";
	
//	private static final String PUT=Scheme.CONTROLLER.getValue()+"://put?unique={}&path={}";
	
	@Override
	public boolean accept(URI uri) {
		boolean accept= Scheme.BEAN.getValue().equals(uri.getScheme());
		return accept;
	}

	@Override
	public Object execute(URI uri,Object object) {
		String beanName=null;
		String query= uri.getQuery();
		Pattern pattern=Pattern.compile(REGX);
		Matcher matcher=pattern.matcher(query);
		if(matcher.matches()){
			beanName=matcher.group(2);
		}
		return applicationContext.getBean(beanName);
	}
	
	static final String getGetRequest(String unique,String path){
		return String.format(URI,Type.GET.getValue(), unique,path);
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
}
