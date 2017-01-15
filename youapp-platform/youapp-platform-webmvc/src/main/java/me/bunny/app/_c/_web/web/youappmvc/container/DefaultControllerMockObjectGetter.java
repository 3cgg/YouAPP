package me.bunny.app._c._web.web.youappmvc.container;

import me.bunny.app._c.data.web.mapping.MappingMeta;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.io.JURIPart;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.proxy.JSimpleProxy;
import me.bunny.kernel._c.proxy.JSimpleProxy.Callback;
import me.bunny.kernel._c.support._package.JDefaultMethodMeta;
import me.bunny.kernel.mock.JDefaultJSONMockService;
import me.bunny.kernel.mock.JDefaultMockURIPrefix;
import me.bunny.kernel.mock.JJSONMockModelParser;
import me.bunny.kernel.mock.JJSONMockService;
import me.bunny.kernel.mock.JMethodNameAsFileParser;
import me.bunny.kernel.mock.JMockContext;
import me.bunny.kernel.mock.JMockModel;
import me.bunny.kernel.mock.JMockProperties;

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
			
			JURIPart<JDefaultMethodMeta> pathRoot=new JDefaultMockURIPrefix(JConfiguration.get().getString(JMockProperties.YOUAPP_MOCK_URI_ROOT));
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
			JURIPart<JDefaultMethodMeta> pathRoot=new JDefaultMockURIPrefix(JConfiguration.get().getString(JMockProperties.YOUAPP_MOCK_URI_ROOT));
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
