package j.jave.kernal.container.rhttp;

import j.jave.kernal.container.JExecutableURIGenerator;
import j.jave.kernal.container.JExecutableURIUtil;
import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.scheme.JScheme;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JRemoteHttpRunner implements JRunner,JExecutableURIGenerator{

	private String unique;
	
	private String name;
	
	private JRemoteHttpMicroContainerConfig remoteHttpMicroContainerConfig;
	
	public JRemoteHttpRunner(JRemoteHttpMicroContainerConfig remoteHttpMicroContainerConfig) {
		this.remoteHttpMicroContainerConfig=remoteHttpMicroContainerConfig;
		this.unique=this.remoteHttpMicroContainerConfig.unique();
		this.name=this.remoteHttpMicroContainerConfig.name();
	}
	
	private Map<String, JRemoteURIInfo> mappingUrls=new ConcurrentHashMap<String, JRemoteURIInfo>();
	
	@Override
	public boolean accept(URI uri) {
		boolean accept= JScheme.REMOTE_HTTP.getValue().equals(uri.getScheme());
		String unique=JExecutableURIUtil.getUnique(uri);
		return accept=accept&&this.unique.equals(unique);
	}

	@Override
	public Object execute(URI uri, Object object) {
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
		return mappingUrls.containsKey(path);
	}
	
	private Object delete(URI uri,Object object){
		String path = getPath(uri);
		return mappingUrls.remove(path);
	}
	
	private Object put(URI uri,Object object){
		String path = getPath(uri);
		return mappingUrls.put(path, (JRemoteURIInfo) object);
	}

	private String getPath(URI uri) {
		return JExecutableURIUtil.getPath(uri);
	}
	
	private Object get(URI uri,Object object){
		String path = getPath(uri);
		return mappingUrls.get(path);
	}

	@Override
	public String unique() {
		return this.unique;
	}

	@Override
	public String name() {
		return this.name;
	}
	
	@Override
	public String getGetRequestURI(String unique, String path) {
		return JExecutableURIUtil.getGetRequestURI(unique, path, JScheme.REMOTE_HTTP);
	}
	
	@Override
	public String getPutRequestURI(String unique, String path) {
		return JExecutableURIUtil.getPutRequestURI(unique, path, JScheme.REMOTE_HTTP);
	}
	@Override
	public String getDeleteRequestURI(String unique, String path) {
		return JExecutableURIUtil.getDeleteRequestURI(unique, path, JScheme.REMOTE_HTTP);
	}
	
	@Override
	public String getExistRequestURI(String unique, String path) {
		return JExecutableURIUtil.getExistRequestURI(unique, path, JScheme.REMOTE_HTTP);
	}

}
