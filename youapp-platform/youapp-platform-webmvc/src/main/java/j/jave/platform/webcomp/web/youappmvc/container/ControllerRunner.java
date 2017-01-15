package j.jave.platform.webcomp.web.youappmvc.container;

import me.bunny.app._c.data.web.mapping.MappingMeta;
import me.bunny.app._c.sps.core.SpringDynamicJARApplicationContext;
import me.bunny.app._c.sps.multiv.ComponentVersionApplication;
import me.bunny.kernel._c.exception.JOperationNotSupportedException;
import me.bunny.kernel.container.JExecutableURIGenerator;
import me.bunny.kernel.container.JExecutableURIUtil;
import me.bunny.kernel.container.JRunner;
import me.bunny.kernel.container.JScheme;

import java.net.URI;
import java.util.Collections;
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
		this.unique=controllerMicroContainerConfig.unique();
		this.name=controllerMicroContainerConfig.name();
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
		return accept=accept&&this.unique.equals(unique);
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
		if(ControllerUrlNames.GET_ALL_CONTROLLERS_URL.equals(path)){
			return Collections.unmodifiableCollection(mappingMetas.values());
		}
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
