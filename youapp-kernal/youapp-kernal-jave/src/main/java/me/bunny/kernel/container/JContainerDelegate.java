package me.bunny.kernel.container;

import java.net.URI;
import java.util.Collection;

import me.bunny.kernel._c.exception.JOperationNotSupportedException;
import me.bunny.kernel.container.listener.JContainerGetEvent;
import me.bunny.kernel.container.listener.JContainerRegisterEvent;
import me.bunny.kernel.container.listener.JContainerUniquesGetEvent;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

/**
 * delegate all services to the {@link JContainerService}
 * @author JIAZJ
 * @see JContainerService
 */
public class JContainerDelegate {

	private JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	private static JContainerDelegate containerDelegate=new JContainerDelegate();
	
	private JContainerDelegate() {
	}
	
	public static JContainerDelegate get(){
		return containerDelegate;
	}
	
	public <M extends JContainer> M getContainer(String unique){
		return (M) serviceHubDelegate.addImmediateEvent(new JContainerGetEvent(this, unique),JContainer.class);
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
	
	/**
	 * get all containers, the event triggered by the passed argument object (#source)
	 * @param source the event triggering object.
	 * @return All containers unique
	 */
	@SuppressWarnings("unchecked")
	public Collection<String> getAllContainerUniques(Object source){
		return serviceHubDelegate.addImmediateEvent(new JContainerUniquesGetEvent(source),Collection.class);
	}
	
}
