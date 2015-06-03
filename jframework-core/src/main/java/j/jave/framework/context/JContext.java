package j.jave.framework.context;

import j.jave.framework.extension.base64.JIBase64Factory;
import j.jave.framework.extension.http.JIHttpFactory;
import j.jave.framework.extension.logger.JILoggerFactory;
import j.jave.framework.servicehub.JServiceFactoryManager.ServiceMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * the context goes through all the application. 
 * @author J
 * {@link #EXT_LOGGER_FACTORY_IMPLEMENTAION}
 * {@link #EXT_BASE64_FACTORY_IMPLEMENTAION}
 * {@link #EXT_SERVICE_FACTORIES_REGISTER}
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
	
	
	private static final String EXT_LOGGER_FACTORY_IMPLEMENTAION="j.jave.framework.logging.JLogger";

	public void setLoggerFactory(Class<? extends JILoggerFactory> clazz){
		put(EXT_LOGGER_FACTORY_IMPLEMENTAION, clazz);
	}
	
	public void setLoggerFactory(JILoggerFactory loggerFactory){
		put(EXT_LOGGER_FACTORY_IMPLEMENTAION, loggerFactory);
	}

	public Object getLoggerFactory(){
		return get(EXT_LOGGER_FACTORY_IMPLEMENTAION);
	}

	private static final String EXT_SERVICE_FACTORIES_REGISTER="j.jave.framework.servicehub.JServiceFactoryRegister";
	{
		put(EXT_SERVICE_FACTORIES_REGISTER, new ArrayList<ServiceMeta>());
	}
	
	@SuppressWarnings("unchecked")
	public void setServiceMeta(ServiceMeta serviceMeta){
		((List<ServiceMeta>)get(EXT_SERVICE_FACTORIES_REGISTER)).add(serviceMeta);
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceMeta> getServiceMetas(){
		return (List<ServiceMeta>)get(EXT_SERVICE_FACTORIES_REGISTER);
	}
	
	
	private static final String EXT_BASE64_FACTORY_IMPLEMENTAION="j.jave.framework.extension.base64.JIBase64Factory";

	public void setBase64Factory(Class<? extends JIBase64Factory> clazz){
		put(EXT_BASE64_FACTORY_IMPLEMENTAION, clazz);
	}
	
	public void setBase64Factory(JIBase64Factory factory){
		put(EXT_BASE64_FACTORY_IMPLEMENTAION, factory);
	}
	
	public Object getBase64Factory(){
		return  get(EXT_BASE64_FACTORY_IMPLEMENTAION);
	}
	
	
	private static final String EXT_HTTP_FACTORY_IMPLEMENTAION="j.jave.framework.extension.http.JIHttpFactory";

	public void setHttpFactory(Class<? extends JIHttpFactory> clazz){
		put(EXT_HTTP_FACTORY_IMPLEMENTAION, clazz);
	}
	
	public void setHttpFactory(JIHttpFactory httpFactory){
		put(EXT_HTTP_FACTORY_IMPLEMENTAION, httpFactory);
	}

	public Object getHttpFactory(){
		return get(EXT_HTTP_FACTORY_IMPLEMENTAION);
	}
	
}
