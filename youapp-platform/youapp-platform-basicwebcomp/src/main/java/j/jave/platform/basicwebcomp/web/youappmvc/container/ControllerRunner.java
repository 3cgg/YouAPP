package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.Scheme;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationContext;
import j.jave.platform.basicsupportcomp.core.container.MappingMeta;
import j.jave.platform.multiversioncompsupportcomp.ComponentVersionApplication;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;

public class ControllerRunner implements JRunner {

	private String unique;
	
	private String name;
	
	private final ApplicationContext applicationContext;
	
	private final ComponentVersionApplication componentVersionApplication;
	
	private ControllerMicroContainerConfig controllerMicroContainerConfig;
	
	private final Map<String, MappingMeta> mappingMetas=new ConcurrentHashMap<String, MappingMeta>(128);

	public ControllerRunner(ApplicationContext applicationContext,ComponentVersionApplication componentVersionApplication,
			ControllerMicroContainerConfig controllerMicroContainerConfig) {
		this.applicationContext=applicationContext;
		this.componentVersionApplication=componentVersionApplication;
		if(SpringDynamicJARApplicationContext.class.isInstance(applicationContext)){
			mappingMetas.putAll(((SpringDynamicJARApplicationContext)applicationContext).getMappingMetas());
		}
		this.controllerMicroContainerConfig=controllerMicroContainerConfig;
	}
	
	@Override
	public String unique() {
		return unique;
	}
	
	@Override
	public String name() {
		return name;
	}
	
	public static enum Type{
		
		GET("get"),PUT("put"),DELETE("delete"),EXIST("exist");
		
		private String value;
		
		Type(String value){
			this.value=value;
		}
		public String getValue() {
			return value;
		}
	}
	
	private static final String UNIQUE="unique";
	
	private static final String PATH="path";
	
	/**
	 * controller://get/put/delete?unique=%s&path=%s
	 */
	private static final String URI=Scheme.CONTROLLER.getValue()+"://&s?"+UNIQUE+"=%s&"+PATH+"=%s";
	
	/**
	 * ^unique=([a-zA-Z:0-9_]+)&path=([a-zA-Z:0-9_]+)$
	 */
	private static final String REGX="^"+UNIQUE+"=([a-zA-Z:0-9_]+)&"+PATH+"=([a-zA-Z:0-9_]+)$";
	
//	private static final String PUT=Scheme.CONTROLLER.getValue()+"://put?unique={}&path={}";
	
	static final String getGetRequest(String unique,String path){
		return String.format(URI,Type.GET.getValue(),unique,path);
	}
	
	static final String getExistRequest(String unique,String path){
		return String.format(URI,Type.EXIST.getValue(),unique,path);
	}
	
	static final String getPutRequest(String unique,String path){
		return String.format(URI,Type.PUT.getValue(),unique,path);
	}
	
	@Override
	public final boolean accept(URI uri) {
		boolean accept= Scheme.CONTROLLER.getValue().equals(uri.getScheme());
		String query= uri.getQuery();
		Pattern pattern=Pattern.compile(REGX);
		Matcher matcher=pattern.matcher(query);
		String unique=null;
		String path=null;
		if(matcher.matches()){
			unique=matcher.group(1);
			path=matcher.group(2);
		}
		return accept=accept&&unique.equals(unique)&&mappingMetas.containsKey(path);
	}

	@Override
	public Object execute(URI uri,Object object) {
		String type=uri.getSchemeSpecificPart();
		if(Type.GET.value.equals(type)){
			return get(uri, object);
		}
		else if(Type.PUT.value.equals(type)){
			return put(uri, object);
		}
		return null;
	}
	
	private Object put(URI uri,Object object){
		String path = getPath(uri);
		return mappingMetas.put(path, (MappingMeta) object);
	}

	private String getPath(URI uri) {
		String query= uri.getQuery();
		Pattern pattern=Pattern.compile(REGX);
		Matcher matcher=pattern.matcher(query);
		String path=null;
		if(matcher.matches()){
			path=matcher.group(2);
		}
		return path;
	}
	
	private Object get(URI uri,Object object){
		String path = getPath(uri);
		return mappingMetas.get(path);
	}
	
}
