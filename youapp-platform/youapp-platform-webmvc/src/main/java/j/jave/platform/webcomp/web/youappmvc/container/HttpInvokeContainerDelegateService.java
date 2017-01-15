package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.platform.data.web.mapping.MappingMeta;
import j.jave.platform.sps.multiv.ComponentVersionTestApplication;
import j.jave.platform.sps.multiv.DynamicComponentVersionApplication;
import j.jave.platform.sps.multiv.PlatformComponentVersionApplication;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel.container.JContainer;
import me.bunny.kernel.container.JContainerDelegate;
import me.bunny.kernel.container.JExecutableURIUtil;
import me.bunny.kernel.container.JScheme;
import me.bunny.kernel.container.rhttp.JRemoteHttpContainerConfig;
import me.bunny.kernel.container.rhttp.JRemoteHttpInvokeContainer;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HttpInvokeContainerDelegateService
extends JServiceFactorySupport<HttpInvokeContainerDelegateService> 
implements JService{

	private JContainerDelegate containerDelegate=JContainerDelegate.get();
	
	private static HttpInvokeContainerDelegateService requestInvokeContainerDelegate=new HttpInvokeContainerDelegateService();
	
	public HttpInvokeContainerDelegateService() {
	}
	
	private static HttpInvokeContainerDelegateService get(){
		return requestInvokeContainerDelegate;
	}
	
	@Override
	protected HttpInvokeContainerDelegateService doGetService() {
		return get();
	}
	
	public boolean accept(URI uri,String containerUnique){
		return containerDelegate.accept(uri, containerUnique, false);
	}
	
	public boolean accept(URI uri,String containerUnique,boolean throwsException){
		return containerDelegate.accept(uri, containerUnique, throwsException);
	}
	
	/**
	 * the method also check if the container can accept the request(URI).
	 * it is the same as {@link #execute(URI, Object, String, boolean)} , in which boolean argument is false.
	 * @param uri
	 * @param object
	 * @param containerUnique
	 * @return
	 */
	public Object execute(URI uri,Object object,String containerUnique){
		return containerDelegate.execute(uri, object,containerUnique);
	}
	
	/**
	 * execute the request/URI in the certain container.
	 * @param uri
	 * @param object
	 * @param containerUnique
	 * @param skipCheck  if skip the accept check.
	 * @return
	 */
	public Object execute(URI uri,Object object,String containerUnique,boolean skipCheck){
		return containerDelegate.execute(uri, object,containerUnique,skipCheck);
	}
	
	public boolean exist(String path,String containerUnique){
		String existURI=getExistRequestURI(containerUnique, path);
		try {
			if(JStringUtils.isNullOrEmpty(existURI)) return false;
			return (boolean) containerDelegate.execute(new URI(existURI), null, containerUnique, false);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Collection<String> getAllContainerUniques(){
		return containerDelegate.getAllContainerUniques(this);
	}
	
	/**
	 * scan all containers to check if the path is existing.
	 * @param path
	 * @return
	 */
	public boolean exist(String path){
		Collection<String> uniques= getAllContainerUniques();
		boolean exist=false;
		for(String unique:uniques){
			exist=exist||exist(path, unique);
		}
		return exist;
	}
	
	public List<ContainerMappingMeta> getRuntimeAllMappingMetas(){
		List<ContainerMappingMeta> containerMappingMetas=new ArrayList<ContainerMappingMeta>();
		try{
			Collection<String> uniques= getAllContainerUniques();
			for(String unique:uniques){
				ContainerMappingMeta containerMappingMeta= getRuntimeMappingMeta(unique);
				if(containerMappingMeta!=null){
					containerMappingMetas.add(containerMappingMeta);
				}
			}
			return containerMappingMetas;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		return Collections.EMPTY_LIST;
	}
	
	public ContainerMappingMeta getRuntimeMappingMeta(String unique){
		ContainerMappingMeta containerMappingMeta=null;
		try{
			String getAllUrl=getGetAllRequestURI(unique);
			if(JStringUtils.isNullOrEmpty(getAllUrl)) return null;
			Object object=execute(new URI(getAllUrl), null, unique);
			if(Collection.class.isInstance(object)){
				containerMappingMeta=new ContainerMappingMeta();
				containerMappingMeta.setUnique(unique);
				Collection<MappingMeta> mappingMetas=new ArrayList<MappingMeta>();
				Collection<?> coll=(Collection<?>) object;
				for(Object obj:coll){
					if(!(obj instanceof MappingMeta)){
						break;
					}
					mappingMetas.add((MappingMeta) obj);
				}
				containerMappingMeta.setMappingMetas(mappingMetas);
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return containerMappingMeta;
	}
	
	/**
	 * new a container instance... 
	 * @param config
	 * @param dynamicComponentVersionApplication
	 * @return the container unique
	 */
	public String newInstance(InnerHttpInvokeContainerConfig config,DynamicComponentVersionApplication dynamicComponentVersionApplication){
		JContainer container=new InnerHttpInvokeContainer(config,dynamicComponentVersionApplication);
		container.initialize();
		return container.unique();
	}
	
	/**
	 * startup platform container.
	 * @param springContainerConfig
	 * @param componentVersionApplication
	 * @return
	 */
	public String newInstance(InnerHttpInvokeContainerConfig config,PlatformComponentVersionApplication platformComponentVersionApplication){
		JContainer container=new InnerHttpInvokeContainer(config,platformComponentVersionApplication);
		container.initialize();
		return container.unique();
	}
	
	/**
	 * startup test container for the platform functions.
	 * @param config
	 * @param platformTestComponentVersionApplication
	 * @return
	 */
	public String newInstance(InnerHttpInvokeTestContainerConfig config,
			ComponentVersionTestApplication componentVersionTestApplication,
			InnerHttpInvokeContainer innerHttpInvokeContainer){
		InnerHttpInvokeTestContainer container=new InnerHttpInvokeTestContainer(config,
				componentVersionTestApplication,innerHttpInvokeContainer);
		container.initialize();
		return container.unique();
	}
	
	/**
	 * new a new remote http container...
	 * @param remoteHttpContainerConfig
	 * @return the container unique .
	 */
	public String newInstance(JRemoteHttpContainerConfig remoteHttpContainerConfig){
		JContainer container=new JRemoteHttpInvokeContainer(remoteHttpContainerConfig);
		container.initialize();
		return container.unique();
	}
	
	/**
	 * 
	 * @param unique  the container unique
	 * @param path the relative path of host
	 * @return
	 */
	public static final String getControllerRequestGetURI(String unique,String path){
		return JExecutableURIUtil.getGetRequestURI(unique, path, JScheme.CONTROLLER);
	}
	
	/**
	 * 
	 * @param unique  the container unique
	 * @param path the relative path of host
	 * @return
	 */
	public static final String getControllerRequestExistURI(String unique,String path){
		return JExecutableURIUtil.getExistRequestURI(unique, path, JScheme.CONTROLLER);
	}
	
	/**
	 * 
	 * @param unique  the container unique
	 * @param path the relative path of host
	 * @return
	 */
	public static final String getControllerRequestPutURI(String unique,String path){
		return JExecutableURIUtil.getPutRequestURI(unique, path, JScheme.CONTROLLER);
	}

	/**
	 * 
	 * @param unique the container unique
	 * @param beanName the spring bean name
	 * @return
	 */
	public static final String getBeanRequestGetURI(String unique,String beanName){
		return JExecutableURIUtil.getGetRequestURI(unique, beanName, JScheme.BEAN);
	}

	/**
	 * 
	 * @param unique  the container unique
	 * @param path the relative path of host
	 * @return
	 */
	public String getExistRequestURI(String unique, String path) {
		return new BaseHttpURLExpose(unique, path).getExistRequestURI();
	}
	
	/**
	 * 
	 * @param unique  the container unique
	 * @param path the relative path of host
	 * @return
	 */
	public String getExecuteRequestURI(String unique, String path){
		return new BaseHttpURLExpose(unique, path).getExecuteRequestURI();
	}
	
	/**
	 * 
	 * @param unique  the container unique
	 * @param path the relative path of host
	 * @return
	 */
	public String getGetRequestURI(String unique, String path){
		return new BaseHttpURLExpose(unique, path).getGetRequestURI();
	}
	
	
	/**
	 * 
	 * @param unique  the container unique
	 * @param path the relative path of host
	 * @return
	 */
	public String getPutRequestURI(String unique, String path){
		return new BaseHttpURLExpose(unique, path).getPutRequestURI();
	}
	
	/**
	 * 
	 * @param unique  the container unique
	 * @param path the relative path of host
	 * @return
	 */
	public String getDeleteRequestURI(String unique, String path){
		return new BaseHttpURLExpose(unique, path).getDeleteRequestURI();
	}
	
	/**
	 * 
	 * @param unique  the container unique
	 * @param path the relative path of host
	 * @return
	 */
	public String getGetAllRequestURI(String unique){
		return getGetRequestURI(unique, ControllerUrlNames.GET_ALL_CONTROLLERS_URL);
	}
}
