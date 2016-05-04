package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.JContainer;
import j.jave.kernal.container.JContainerDelegate;
import j.jave.kernal.container.JExecutableURIUtil;
import j.jave.kernal.container.JScheme;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicsupportcomp.core.container.DynamicSpringContainerConfig;
import j.jave.platform.basicsupportcomp.core.container.SpringContainerConfig;
import j.jave.platform.multiversioncompsupportcomp.ComponentVersionTestApplication;
import j.jave.platform.multiversioncompsupportcomp.DynamicComponentVersionApplication;
import j.jave.platform.multiversioncompsupportcomp.PlatformComponentVersionApplication;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

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
	public HttpInvokeContainerDelegateService getService() {
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
			if(JStringUtils.isNullOrEmpty(existURI)) return true;
			return (boolean) containerDelegate.execute(new URI(existURI), null, containerUnique, false);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * scan all containers to check if the path is existing.
	 * @param path
	 * @return
	 */
	public boolean exist(String path){
		Collection<String> uniques= containerDelegate.getAllContainerUniques(this);
		boolean exist=false;
		for(String unique:uniques){
			exist=exist||exist(path, unique);
		}
		return exist;
	}
	
	/**
	 * new a container instance... 
	 * @param dynamicSpringContainerConfig
	 * @param dynamicComponentVersionApplication
	 * @return the container unique
	 */
	public String newInstance(DynamicSpringContainerConfig dynamicSpringContainerConfig,DynamicComponentVersionApplication dynamicComponentVersionApplication){
		JContainer container=new InnerHttpInvokeContainer(dynamicSpringContainerConfig,dynamicComponentVersionApplication);
		container.initialize();
		return container.unique();
	}
	
	/**
	 * startup platform container.
	 * @param springContainerConfig
	 * @param componentVersionApplication
	 * @return
	 */
	public String newInstance(SpringContainerConfig springContainerConfig,PlatformComponentVersionApplication platformComponentVersionApplication){
		JContainer container=new InnerHttpInvokeContainer(springContainerConfig,platformComponentVersionApplication);
		container.initialize();
		return container.unique();
	}
	
	/**
	 * startup test container for the platform functions.
	 * @param springContainerConfig
	 * @param platformTestComponentVersionApplication
	 * @return
	 */
	public String newInstance(SpringContainerConfig springContainerConfig,
			ComponentVersionTestApplication componentVersionTestApplication,
			InnerHttpInvokeContainer innerHttpInvokeContainer){
		JContainer container=new InnerHttpInvokeTestContainer(springContainerConfig,
				componentVersionTestApplication,innerHttpInvokeContainer);
		container.initialize();
		return container.unique();
	}
	
	public static final String getControllerRequestGetURI(String unique,String path){
		return JExecutableURIUtil.getGetRequestURI(unique, path, JScheme.CONTROLLER);
	}
	
	public static final String getControllerRequestExistURI(String unique,String path){
		return JExecutableURIUtil.getExistRequestURI(unique, path, JScheme.CONTROLLER);
	}
	
	public static final String getControllerRequestPutURI(String unique,String path){
		return JExecutableURIUtil.getPutRequestURI(unique, path, JScheme.CONTROLLER);
	}

	public static final String getBeanRequestGetURI(String unique,String beanName){
		return JExecutableURIUtil.getGetRequestURI(unique, beanName, JScheme.BEAN);
	}

	public String getExistRequestURI(String unique, String path) {
		return new BaseHttpURLExpose(unique, path).getExistRequestURI();
	}
	
	public String getExecuteRequestURI(String unique, String path){
		return new BaseHttpURLExpose(unique, path).getExecuteRequestURI();
	}
	
	
}
