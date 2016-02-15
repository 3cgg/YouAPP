package j.jave.kernal.eventdriven.context;

import j.jave.kernal.eventdriven.servicehub.JEventQueuePipeChain.JEventQueuePipeInfo;
import j.jave.kernal.eventdriven.servicehub.JEventQueuePipeChain.JEventQueuePipeProvider;
import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager.JServiceMetaProvider;
import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager.ServiceMeta;
import j.jave.kernal.jave.support.JPriorityBlockingQueue;

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
public class JEventDrivenContext extends HashMap<String, Object> {
	
	private static JEventDrivenContext context=new JEventDrivenContext();
	
	private JEventDrivenContext() {
	}
	
	public static JEventDrivenContext get(){
//		if(context==null){
//			synchronized (JCommonsEventDrivenContext.class) {
//				if(context==null){
//					context=new JCommonsEventDrivenContext();
//				}
//			}
//		}
		return context;
	}

	/**
	 * some additional service factories can be append the running system.
	 * @author JIAZJ
	 *
	 */
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
	
	/**
	 * some additional event queue processing can be registered here.
	 * @author JIAZJ
	 *
	 */
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
