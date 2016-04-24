package j.jave.kernal.container;

import j.jave.kernal.container.eventdriven.JContainerGetEvent;
import j.jave.kernal.container.eventdriven.JContainerUniquesGetEvent;
import j.jave.kernal.container.eventdriven.JContainerRegisterEvent;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;

import java.net.URI;
import java.util.Collection;

public class JContainerDelegate {

	private JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	private static JContainerDelegate containerDelegate=new JContainerDelegate();
	
	private JContainerDelegate() {
	}
	
	public static JContainerDelegate get(){
		return containerDelegate;
	}
	
	public JContainer getContainer(String unique){
		return serviceHubDelegate.addImmediateEvent(new JContainerGetEvent(this, unique),JContainer.class);
	}
	
	public boolean accept(URI uri,String containerUnique){
		return this.accept(uri, containerUnique, false);
	}
	
	public boolean accept(URI uri,String containerUnique,boolean throwsException){
		JContainer container=getContainer(containerUnique);
		if(container==null){
			throw new JOperationNotSupportedException("the container: ["+containerUnique+"] doesnot exists.");
		}
		if(JExecutor.class.isInstance(container)){
			JExecutor executor=(JExecutor) container;
			return executor.accept(uri);
		}
		else{
			if(throwsException){
				throw new JOperationNotSupportedException("the container["+containerUnique+"] cannot receive request.");
			}
			else{
				return false;
			}
		}
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
		accept(uri, containerUnique,true);
		JExecutor executor=(JExecutor)getContainer(containerUnique);
		return executor.execute(uri, object);
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
		if(skipCheck){
			accept(uri, containerUnique,true);
		}
		JExecutor executor=(JExecutor)getContainer(containerUnique);
		return executor.execute(uri, object);
	}
	
	public void register(JContainer container){
		serviceHubDelegate.addImmediateEvent(new JContainerRegisterEvent(this, container));
	}
	
	@SuppressWarnings("unchecked")
	public Collection<String> getAllContainerUniques(Object source){
		return serviceHubDelegate.addImmediateEvent(new JContainerUniquesGetEvent(source),Collection.class);
	}
	
}
