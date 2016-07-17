package test.j.jave.kernal.mock;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.io.JURIPart;
import j.jave.kernal.jave.support._package.JDefaultMethodMeta;
import j.jave.kernal.mock.JDefaultJSONMockService;
import j.jave.kernal.mock.JDefaultMockURIPrefix;
import j.jave.kernal.mock.JJSONMockModelParser;
import j.jave.kernal.mock.JJSONMockService;
import j.jave.kernal.mock.JMethodNameAsFileParser;
import j.jave.kernal.mock.JMockClassMockModelParser;
import j.jave.kernal.mock.JMockContext;
import j.jave.kernal.mock.JMockModel;
import j.jave.kernal.mock.JMockProperties;

import org.junit.Test;

import test.j.jave.kernal.eventdriven.TestEventSupport;

public class TestMock extends TestEventSupport {

	
	@Test
	public void testMock() throws Exception{
		JConfiguration configuration=JConfiguration.get();
		JURIPart<JDefaultMethodMeta> pathRoot=new JDefaultMockURIPrefix(configuration.getString(JMockProperties.YOUAPP_MOCK_URI_ROOT));
		JJSONMockModelParser mockModelParser=new JMethodNameAsFileParser(pathRoot);
		JMockContext context=new JMockContext();
		JDefaultMethodMeta methodMeta=new JDefaultMethodMeta();
		methodMeta.setClazz(this.getClass());
		methodMeta.setMethodName("testMock");
		JMockModel mockModel=mockModelParser.parse(methodMeta, context);
		System.out.println(mockModel); 
		
		JJSONMockService jsonMockService= new JDefaultJSONMockService();
		Object data=jsonMockService.mockData(mockModel);
		System.out.println(data);
		
	}
	
	@Test
	public void testClassMock() throws Exception{
		JJSONMockModelParser mockModelParser=new JMockClassMockModelParser();
		JMockContext context=new JMockContext();
		context.put("name", " for mock param");
		context.put("arg0", " for mock param");
		JDefaultMethodMeta methodMeta=new JDefaultMethodMeta(SetService.class,SetService.class.getDeclaredMethod("name", String.class));
		JMockModel mockModel=mockModelParser.parse(methodMeta, context);
		System.out.println(mockModel); 
		
		JJSONMockService jsonMockService= new JDefaultJSONMockService();
		Object data=jsonMockService.mockData(mockModel);
		System.out.println(data);
		
	}
	
	
}
