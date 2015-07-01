package j.jave.framework.commons.eventdriven.context;

import j.jave.framework.commons.eventdriven.servicehub.JEventQueuePipes.JEventQueuePipeInfo;
import j.jave.framework.commons.eventdriven.servicehub.JEventQueuePipes.JEventQueuePipeProvider;
import j.jave.framework.commons.eventdriven.servicehub.JServiceFactoryManager.JServiceMetaProvider;
import j.jave.framework.commons.eventdriven.servicehub.JServiceFactoryManager.ServiceMeta;
import j.jave.framework.commons.support.JPriorityBlockingQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * the context goes through all the application.
 * the instance also providers the uniform initialization entrance for all extension plugins.
 * @author J
 * @see  
 * <p>{@link #getServiceMetaProvider()}
 */
@SuppressWarnings("serial")
public class JCommonsEventDrivenContext extends HashMap<String, Object> {
	
	private static JCommonsEventDrivenContext context;
	
	private JCommonsEventDrivenContext() {
	}
	
	public static JCommonsEventDrivenContext get(){
		if(context==null){
			synchronized (JCommonsEventDrivenContext.class) {
				if(context==null){
					context=new JCommonsEventDrivenContext();
				}
			}
		}
		return context;
	}

	public class ServiceMetaProvider implements JServiceMetaProvider{
		private static final String EXT_SERVICE_FACTORIES_REGISTER="j.jave.framework.servicehub.JServiceFactoryRegister";
		{
			put(EXT_SERVICE_FACTORIES_REGISTER, new ArrayList<ServiceMeta>());
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public void addServiceMeta(ServiceMeta serviceMeta){
			((List<ServiceMeta>)get(EXT_SERVICE_FACTORIES_REGISTER)).add(serviceMeta);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public List<ServiceMeta> getServiceMetas(){
			return (List<ServiceMeta>)get(EXT_SERVICE_FACTORIES_REGISTER);
		}
	}
	
	public class EventQueuePipeProvider implements JEventQueuePipeProvider{
		
		private final List<JEventQueuePipeInfo> repo=new ArrayList<JEventQueuePipeInfo>(6);
		
		@Override
		public void addEventQueuePipe(JEventQueuePipeInfo eventQueuePipeInfo) {
			repo.add(eventQueuePipeInfo);
		}

		@Override
		public JPriorityBlockingQueue<JEventQueuePipeInfo> getEventQueuePipes() {
			
			JPriorityBlockingQueue<JEventQueuePipeInfo> out=new JPriorityBlockingQueue<JEventQueuePipeInfo>();
			for(int i=0;i<repo.size();i++){
				JEventQueuePipeInfo eventQueuePipeInfo= repo.get(i);
				out.offer(eventQueuePipeInfo);
			}
			return out;
		}
		
	}
	
	private ServiceMetaProvider serviceMetaProvider=new ServiceMetaProvider();

	private EventQueuePipeProvider eventQueuePipeProvider=new EventQueuePipeProvider();
	
	public EventQueuePipeProvider getEventQueuePipeProvider() {
		return eventQueuePipeProvider;
	}
	

	public ServiceMetaProvider getServiceMetaProvider() {
		return serviceMetaProvider;
	}
	
}
