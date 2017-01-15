package me.bunny.kernel.mock;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

import me.bunny.kernel._c.reflect.JClassUtils;
import me.bunny.kernel._c.reflect.JReflectionUtils;
import me.bunny.kernel._c.support._package.JDefaultMethodMeta;
import me.bunny.kernel._c.support._package.JDefaultParamMeta;
import me.bunny.kernel.container.JExecutableURIUtil;

public class JMockClassMockModelParser implements JJSONMockModelParser {

	private static class JDefaultMockClassGetter implements JMockClassGetter{
		@Override
		public Class<?> getMockClass(JDefaultMethodMeta methodMeta,
				JMockContext context) throws Exception{
			Class<?> clazz=methodMeta.getClazz();
			String mockClassName="mock."+clazz.getName();
			Class<?> mockClass=JClassUtils.load(mockClassName);
			return mockClass;
		}
	}
	
	
	private static class JDefaultMethodParamsParser implements JMockClassMethodParamsParser{
		@Override
		public Object[] parseMethodParams(
				JDefaultMethodMeta defaultMethodMetal, JMockContext context) {
			JDefaultParamMeta[] defaultParamMetas=defaultMethodMetal.getParamMetas();
			Object[] objs=new Object[defaultMethodMetal.getParamMetas().length];
			int i=0;
			for(JDefaultParamMeta defaultParamMeta:defaultParamMetas){
				objs[i++]=context.get(defaultParamMeta.getName());
			}
			return objs;
		}
	}
	
	
	private static JMockClassGetter mockClassGetter
	=new JDefaultMockClassGetter();
	
	private static JMockClassMethodParamsParser methodParamsParser
	=new JDefaultMethodParamsParser();
	
	private JDefaultJSONMockModelParser defaultJSONMockModelParser
	=new JDefaultJSONMockModelParser(); 
	
	private JMethodNameAsFileParser methodNameAsFileParser
	=new JMethodNameAsFileParser();
	
	@Override
	public JMockModel parse(JDefaultMethodMeta methodMeta, JMockContext context) {
		Class<?> mockClass=null;
		try {
			mockClass=mockClassGetter.getMockClass(methodMeta, context);
		} catch (Exception e) {
		}
		Method method=null;
		try {
			if(mockClass!=null){
				method=mockClass.getDeclaredMethod(methodMeta.getMethodName(), methodMeta.getParameterTypes());
			}
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		}
		
		if(method==null){
			return methodNameAsFileParser.parse(methodMeta, context);
		}
		else{
			JDefaultMethodMeta mockMethodMeta=new JDefaultMethodMeta(mockClass,method);
			Object[] params=methodParamsParser.parseMethodParams(mockMethodMeta, context);
			Object data=null;
			try {
				data=JReflectionUtils.invoke(mockClass.newInstance(), method, params);
			}catch (Exception e) {
			}
			JMockModel mockModel= defaultJSONMockModelParser.parse(mockMethodMeta, context);
			if(data!=null){
				try{
					if(data instanceof String){
						String dataStr=String.valueOf(data);
						URI uri=null;
						try{
							uri=new URI(dataStr);
							if(JExecutableURIUtil.isAcceptURI(uri)){
								mockModel.setUri(dataStr);
							}
							else{
								mockModel.setData(dataStr);
							}
						}catch(URISyntaxException e){
							mockModel.setData(dataStr);
						}
					}
					else{
						mockModel.setData(data);
					}
				}catch(Exception e){
				}
			}
			
			return mockModel;
		}
	}

}
