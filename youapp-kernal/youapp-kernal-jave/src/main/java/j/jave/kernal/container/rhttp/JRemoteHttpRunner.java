package j.jave.kernal.container.rhttp;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.RemoteRequestURI;
import j.jave.kernal.container.Scheme;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JRemoteHttpRunner implements JRunner{

	private JRemoteHttpMicroContainerConfig remoteHttpMicroContainerConfig;
	
	public JRemoteHttpRunner(JRemoteHttpMicroContainerConfig remoteHttpMicroContainerConfig) {
		this.remoteHttpMicroContainerConfig=remoteHttpMicroContainerConfig;
	}
	
	private Map<String, String> mappingUrls=new ConcurrentHashMap<String, String>();
	
	@Override
	public boolean accept(URI uri) {
		boolean accept= Scheme.REMOTE_HTTP.getValue().equals(uri.getScheme());
		String query= uri.getQuery();
		Pattern pattern=Pattern.compile(RemoteRequestURI.REGX);
		Matcher matcher=pattern.matcher(query);
		String unique=null;
		String path=null;
		if(matcher.matches()){
			unique=matcher.group(1);
			path=matcher.group(2);
		}
		return accept=accept&&unique.equals(unique);
	}

	@Override
	public Object execute(URI uri, Object object) {
		String type=uri.getPath();
		if(RemoteRequestURI.Type.GET.getValue().equals(type)){
			return get(uri, object);
		}
		else if(RemoteRequestURI.Type.PUT.getValue().equals(type)){
			return put(uri, object);
		}
		else if(RemoteRequestURI.Type.EXIST.getValue().equals(type)){
			return exist(uri, object);
		}
		else if(RemoteRequestURI.Type.DELETE.getValue().equals(type)){
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
		return mappingUrls.put(path, (String) object);
	}

	private String getPath(URI uri) {
		String query= uri.getQuery();
		Pattern pattern=Pattern.compile(RemoteRequestURI.REGX);
		Matcher matcher=pattern.matcher(query);
		String path=null;
		if(matcher.matches()){
			path=matcher.group(2);
		}
		return path;
	}
	
	private Object get(URI uri,Object object){
		String path = getPath(uri);
		return mappingUrls.get(path);
	}

	@Override
	public String unique() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

}
