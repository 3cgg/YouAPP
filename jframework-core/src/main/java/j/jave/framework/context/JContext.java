package j.jave.framework.context;

import j.jave.framework.extension.base64.JBase64FactoryProvider;
import j.jave.framework.extension.base64.JIBase64Factory;
import j.jave.framework.extension.http.JHttpFactoryProvider;
import j.jave.framework.extension.http.JIHttpFactory;
import j.jave.framework.extension.logger.JILoggerFactory;
import j.jave.framework.extension.logger.JLoggerFactoryProvider;
import j.jave.framework.servicehub.JEventQueuePipeChain.JEventQueuePipeInfo;
import j.jave.framework.servicehub.JEventQueuePipeChain.JEventQueuePipeProvider;
import j.jave.framework.servicehub.JServiceFactoryManager.JServiceMetaProvider;
import j.jave.framework.servicehub.JServiceFactoryManager.ServiceMeta;
import j.jave.framework.support.JPriorityBlockingQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * the context goes through all the application.
 * the instance also providers the uniform initialization entrance for all extension plugins.
 * @author J
 * @see  
 * <p>{@link #getBase64FactoryProvider()}
 * <p>{@link #getHttpFactoryProvider()}
 * <p>{@link #getLoggerFactoryProvider()}
 * <p>{@link #getServiceMetaProvider()}
 */
@SuppressWarnings("serial")
public class JContext extends HashMap<String, Object> {
	
	private static JContext context;
	
	private JContext() {
	}
	
	public static JContext get(){
		if(context==null){
			synchronized (JContext.class) {
				if(context==null){
					context=new JContext();
				}
			}
		}
		return context;
	}
	
	
	public class LoggerFactoryProvider implements JLoggerFactoryProvider{
		private static final String EXT_LOGGER_FACTORY_IMPLEMENTAION="j.jave.framework.logging.JLogger";

		@Override
		public void setLoggerFactory(Class<? extends JILoggerFactory> clazz){
			put(EXT_LOGGER_FACTORY_IMPLEMENTAION, clazz);
		}
		
		@Override
		public void setLoggerFactory(JILoggerFactory loggerFactory){
			put(EXT_LOGGER_FACTORY_IMPLEMENTAION, loggerFactory);
		}

		@Override
		public Object getLoggerFactory(){
			return get(EXT_LOGGER_FACTORY_IMPLEMENTAION);
		}
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
	

	public class Base64FactoryProvider implements JBase64FactoryProvider{
		private static final String EXT_BASE64_FACTORY_IMPLEMENTAION="j.jave.framework.extension.base64.JIBase64Factory";

		@Override
		public void setBase64Factory(Class<? extends JIBase64Factory> clazz){
			put(EXT_BASE64_FACTORY_IMPLEMENTAION, clazz);
		}
		
		@Override
		public void setBase64Factory(JIBase64Factory factory){
			put(EXT_BASE64_FACTORY_IMPLEMENTAION, factory);
		}
		
		@Override
		public Object getBase64Factory(){
			return  get(EXT_BASE64_FACTORY_IMPLEMENTAION);
		}
	}
	
	
	public  class HttpFactoryProvider implements JHttpFactoryProvider{
		private static final String EXT_HTTP_FACTORY_IMPLEMENTAION="j.jave.framework.extension.http.JIHttpFactory";

		@Override
		public void setHttpFactory(Class<? extends JIHttpFactory> clazz){
			put(EXT_HTTP_FACTORY_IMPLEMENTAION, clazz);
		}
		
		@Override
		public void setHttpFactory(JIHttpFactory httpFactory){
			put(EXT_HTTP_FACTORY_IMPLEMENTAION, httpFactory);
		}

		@Override
		public Object getHttpFactory(){
			return get(EXT_HTTP_FACTORY_IMPLEMENTAION);
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
	
	
	private HttpFactoryProvider httpFactoryProvider=new HttpFactoryProvider();
	
	private Base64FactoryProvider base64FactoryProvider=new Base64FactoryProvider();
	
	private LoggerFactoryProvider loggerFactoryProvider=new LoggerFactoryProvider();
	
	private ServiceMetaProvider serviceMetaProvider=new ServiceMetaProvider();

	private EventQueuePipeProvider eventQueuePipeProvider=new EventQueuePipeProvider();
	
	public EventQueuePipeProvider getEventQueuePipeProvider() {
		return eventQueuePipeProvider;
	}
	
	public HttpFactoryProvider getHttpFactoryProvider() {
		return httpFactoryProvider;
	}

	public Base64FactoryProvider getBase64FactoryProvider() {
		return base64FactoryProvider;
	}

	public LoggerFactoryProvider getLoggerFactoryProvider() {
		return loggerFactoryProvider;
	}

	public ServiceMetaProvider getServiceMetaProvider() {
		return serviceMetaProvider;
	}
	
}
