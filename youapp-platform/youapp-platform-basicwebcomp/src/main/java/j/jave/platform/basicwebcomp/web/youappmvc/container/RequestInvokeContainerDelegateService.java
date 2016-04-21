package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.JContainerDelegate;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

public class RequestInvokeContainerDelegateService
extends JServiceFactorySupport<RequestInvokeContainerDelegateService> implements JService
{

	private JContainerDelegate containerDelegate=JContainerDelegate.get();
	
	private static RequestInvokeContainerDelegateService requestInvokeContainerDelegate=new RequestInvokeContainerDelegateService();
	
	public RequestInvokeContainerDelegateService() {
	}
	
	private static RequestInvokeContainerDelegateService get(){
		return requestInvokeContainerDelegate;
	}
	
	@Override
	public RequestInvokeContainerDelegateService getService() {
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
		String existURI=RequestInvokeContainer.getControllerRequestExistURI(containerUnique, path);
		try {
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
	
}
