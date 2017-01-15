package me.bunny.kernel.eventdriven.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.eventdriven.servicehub.JEventQueuePipe;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryRegister;
import me.bunny.kernel.eventdriven.servicehub.JEventQueuePipeline.JEventQueuePipeInfo;
import me.bunny.kernel.eventdriven.servicehub.JEventQueuePipeline.JEventQueuePipeProvider;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryManager.JServiceMetaProvider;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryManager.ServiceMeta;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.support.JPriorityBlockingQueue;
import me.bunny.kernel.jave.utils.JStringUtils;

/**
 * the context goes through all the application.
 * the instance also providers the uniform initialization entrance for all extension plugins.
 * @author J
 * @see  
 * <p>{@link #getServiceMetaProvider()}
 */
@SuppressWarnings("serial")
public class JEventDrivenContext extends HashMap<String, Object> {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JEventDrivenContext.class);
	
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
		private final String EXT_SERVICE_FACTORIES_REGISTER=JServiceFactoryRegister.class.getName();
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
	class EventQueuePipeProvider implements JEventQueuePipeProvider{
		
		private final List<JEventQueuePipeInfo> repo=new ArrayList<JEventQueuePipeInfo>(6);
		
		/*
		 * youapp.event.pipe.prefix.[indication].class
		 * youapp.event.pipe.prefix.[indication].message
		 * youapp.event.pipe.prefix.[indication].order
		 */
		
		private final static String CLASS_STRING="class";
		private final static String MESSAGE_STRING="message";
		private final static String ORDER_STRING="order";
		
		private EventQueuePipeProvider() {
			//youapp.event.pipe.prefix (JProperties.EVENT_PIPE_PREFIX)
			JConfiguration configuration= JConfiguration.get();
			Set<String> keys=configuration.allKeys("^youapp[.]event[.]pipe[.]prefix[.](.+)[.]class$");
			for(String key:keys){
				String classString=configuration.getString(key, null);
				if(JStringUtils.isNotNullOrEmpty(classString)){
					String orderKey=key.substring(0, key.length()-CLASS_STRING.length())+ORDER_STRING;
					int order=configuration.getInt(orderKey, -1);
					if(order==-1){
						LOGGER.warn(" ignoring event pipe , missing order : "+classString);
						continue; 
					}
					Class<? extends JEventQueuePipe>  clazz=JClassUtils.load(classString);
					JEventQueuePipeInfo eventQueuePipeInfo=new JEventQueuePipeInfo(
							clazz, order);
					
					String messageKey=key.substring(0, key.length()-CLASS_STRING.length())+MESSAGE_STRING;
					String message=configuration.getString(messageKey, null);
					if(JStringUtils.isNotNullOrEmpty(message)){
						eventQueuePipeInfo.setMessage(message);
					}
					addEventQueuePipe(eventQueuePipeInfo);
				}
			}
		}
		
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
	
	public JEventQueuePipeProvider getEventQueuePipeProvider() {
		return eventQueuePipeProvider;
	}
	

	public ServiceMetaProvider getServiceMetaProvider() {
		return serviceMetaProvider;
	}
	
}
