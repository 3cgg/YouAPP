package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.JExecutableURIGenerator;
import j.jave.kernal.container.JExecutableURIUtil;
import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.JScheme;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationContext;
import j.jave.platform.basicsupportcomp.core.container.MappingMeta;
import j.jave.platform.multiversioncompsupportcomp.ComponentVersionApplication;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;

class ControllerRunner implements JRunner, JExecutableURIGenerator {

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

	@Override
	public final boolean accept(URI uri) {
		boolean accept= JScheme.CONTROLLER.getValue().equals(uri.getScheme());
		String unique=JExecutableURIUtil.getUnique(uri);
		return accept=accept&&unique.equals(unique);
	}

	@Override
	public Object execute(URI uri,Object object) {
		String type=uri.getPath();
		if(JExecutableURIUtil.Type.GET.getValue().equals(type)){
			return get(uri, object);
		}
		else if(JExecutableURIUtil.Type.PUT.getValue().equals(type)){
			return put(uri, object);
		}
		else if(JExecutableURIUtil.Type.EXIST.getValue().equals(type)){
			return exist(uri, object);
		}
		else if(JExecutableURIUtil.Type.DELETE.getValue().equals(type)){
			return delete(uri, object);
		}
		
		throw new JOperationNotSupportedException(" the uri ["+uri.toString()+"] not supported."); 
		
	}
	
	private Object exist(URI uri,Object object){
		String path = getPath(uri);
		return mappingMetas.containsKey(path);
	}
	
	private Object delete(URI uri,Object object){
		String path = getPath(uri);
		return mappingMetas.remove(path);
	}
	
	private Object put(URI uri,Object object){
		String path = getPath(uri);
		return mappingMetas.put(path, (MappingMeta) object);
	}

	private String getPath(URI uri) {
		return JExecutableURIUtil.getPath(uri);
	}
	
	private Object get(URI uri,Object object){
		String path = getPath(uri);
		return mappingMetas.get(path);
	}

	@Override
	public String getGetRequestURI(String unique, String path) {
		return JExecutableURIUtil.getGetRequestURI(unique, path, JScheme.CONTROLLER);
	}

	@Override
	public String getPutRequestURI(String unique, String path) {
		return JExecutableURIUtil.getPutRequestURI(unique, path, JScheme.CONTROLLER);
	}

	@Override
	public String getDeleteRequestURI(String unique, String path) {
		return JExecutableURIUtil.getDeleteRequestURI(unique, path, JScheme.CONTROLLER);
	}

	@Override
	public String getExistRequestURI(String unique, String path) {
		return  JExecutableURIUtil.getExistRequestURI(unique, path, JScheme.CONTROLLER);
	}
	
}
