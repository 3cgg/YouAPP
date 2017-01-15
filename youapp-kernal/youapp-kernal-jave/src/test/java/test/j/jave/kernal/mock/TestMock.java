package test.j.jave.kernal.mock;

import org.junit.Test;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.io.JURIPart;
import me.bunny.kernel._c.support._package.JDefaultMethodMeta;
import me.bunny.kernel.mock.JDefaultJSONMockService;
import me.bunny.kernel.mock.JDefaultMockURIPrefix;
import me.bunny.kernel.mock.JJSONMockModelParser;
import me.bunny.kernel.mock.JJSONMockService;
import me.bunny.kernel.mock.JMethodNameAsFileParser;
import me.bunny.kernel.mock.JMockClassMockModelParser;
import me.bunny.kernel.mock.JMockContext;
import me.bunny.kernel.mock.JMockModel;
import me.bunny.kernel.mock.JMockProperties;
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
