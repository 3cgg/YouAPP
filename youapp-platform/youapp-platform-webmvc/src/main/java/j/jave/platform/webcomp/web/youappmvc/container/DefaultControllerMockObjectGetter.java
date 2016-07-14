package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.io.JURIPart;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.proxy.JSimpleProxy;
import j.jave.kernal.jave.proxy.JSimpleProxy.Callback;
import j.jave.kernal.jave.support._package.JDefaultMethodMeta;
import j.jave.kernal.jave.utils.JURIUtils;
import j.jave.kernal.mock.JDefaultJSONMockService;
import j.jave.kernal.mock.JDefaultMockURIPrefix;
import j.jave.kernal.mock.JJSONMockModelParser;
import j.jave.kernal.mock.JJSONMockService;
import j.jave.kernal.mock.JMethodNameAsFileParser;
import j.jave.kernal.mock.JMockContext;
import j.jave.kernal.mock.JMockModel;
import j.jave.kernal.mock.JMockProperties;
import j.jave.platform.data.web.mapping.MappingMeta;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.cglib.proxy.MethodProxy;

/**
 * @author J
 */
public class DefaultControllerMockObjectGetter implements ControllerObjectGetter {

	private Map<Class<?>, Object> mockObjects=new ConcurrentHashMap<Class<?>, Object>();
	
	private Object sync=new Object();
	
	@Override
	public Object getObjet(MappingMeta mappingMeta) throws Exception {
		if(mockObjects.containsKey(mappingMeta.getClazz())){
			return mockObjects.get(mockObjects);
		}
		else{
			synchronized (sync) {
				if(mockObjects.containsKey(mappingMeta.getClazz())){
					return mockObjects.get(mockObjects);
				}
				Object obj=JSimpleProxy.proxy(this, mappingMeta.getClazz(), new MethodNameAsFileCallback());
				mockObjects.put(mappingMeta.getClazz(), obj);
				return obj;
			}
		}
	}
	
	private static class MethodNameAsFileCallback implements Callback{
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			
			JURIPart<JDefaultMethodMeta> pathRoot=new JDefaultMockURIPrefix(JURIUtils.getURIRoot(JConfiguration.get().getString(JMockProperties.YOUAPP_MOCK_URI_ROOT), Thread.currentThread().getContextClassLoader()));
			JJSONMockModelParser mockModelParser=new JMethodNameAsFileParser(pathRoot);
			JMockContext context=new JMockContext();
			JDefaultMethodMeta methodMeta=new JDefaultMethodMeta();
			methodMeta.setClazz(this.getClass());
			methodMeta.setMethodName(method.getName());
			methodMeta.setAnnotations(method.getAnnotations());
			JMockModel mockModel=mockModelParser.parse(methodMeta, context);
			JJSONMockService jsonMockService= new JDefaultJSONMockService();
			String data=(String) jsonMockService.mockData(mockModel);
			return JJSON.get().parse(data, method.getReturnType());
		}

		@Override
		public Object intercept(Object obj, Method method, Object[] args,
				MethodProxy proxy) throws Throwable {
			JURIPart<JDefaultMethodMeta> pathRoot=new JDefaultMockURIPrefix(JURIUtils.getURIRoot(JConfiguration.get().getString(JMockProperties.YOUAPP_MOCK_URI_ROOT), Thread.currentThread().getContextClassLoader()));
			JJSONMockModelParser mockModelParser=new JMethodNameAsFileParser(pathRoot);
			JMockContext context=new JMockContext();
			JDefaultMethodMeta methodMeta=new JDefaultMethodMeta();
			methodMeta.setClazz(this.getClass());
			methodMeta.setMethodName(method.getName());
			methodMeta.setAnnotations(method.getAnnotations());
			JMockModel mockModel=mockModelParser.parse(methodMeta, context);
			JJSONMockService jsonMockService= new JDefaultJSONMockService();
			String data=(String) jsonMockService.mockData(mockModel);
			return JJSON.get().parse(data, method.getReturnType());
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
