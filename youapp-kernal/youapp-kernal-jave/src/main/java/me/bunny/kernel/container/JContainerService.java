package me.bunny.kernel.container;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.container.listener.JContainerGetEvent;
import me.bunny.kernel.container.listener.JContainerGetListener;
import me.bunny.kernel.container.listener.JContainerRegisterEvent;
import me.bunny.kernel.container.listener.JContainerRegisterListener;
import me.bunny.kernel.container.listener.JContainerUniquesGetEvent;
import me.bunny.kernel.container.listener.JContainerUniquesGetListener;
import me.bunny.kernel.jave.exception.JInitializationException;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.service.JService;

/**
 * the container service interfaces, all contains register themselves here.
 * Any function on the container can be register in the service hub via event-listener mechanism.
 * @author JIAZJ
 * @see JContainerGetListener
 * @see JContainerRegisterListener
 * @see JContainerUniquesGetListener
 */
public class JContainerService 
	implements JService, JContainerRegisterListener ,JContainerGetListener,JContainerUniquesGetListener{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JContainerService.class);
	
	public JContainerService(JConfiguration configuration) {
		
	}
	
	public JContainerService() {
	}
	
	private Map<String, JContainer> containers=new ConcurrentHashMap<String, JContainer>();
	
	private ReentrantLock lock=new ReentrantLock();
	
	@Override
	public Object trigger(JContainerRegisterEvent event) {
		try{
			lock.lockInterruptibly();
			JContainer container=event.getContainer();
			if(containers.keySet().contains(container.unique())){
				LOGGER.info("container["+container.unique()+"] already exists, be replace");
			}
			containers.put(container.unique(), container);
			return true;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}finally{
			lock.unlock();
		}
		return false;
	}
	@Override
	public Object trigger(JContainerGetEvent event) {
		String unique=event.getContainerUnique();
		return containers.get(unique);
	}
	@Override
	public Object trigger(JContainerUniquesGetEvent event) {
		return Collections.unmodifiableCollection(containers.keySet());
	}
	
}
