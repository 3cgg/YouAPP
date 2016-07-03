package j.jave.kernal.mock;

import j.jave.kernal.jave.support._package.JDefaultMethodMeta;
import j.jave.kernal.jave.support.parser.JParser;

import java.lang.annotation.Annotation;

public class JDefaultJSONMockModelParser implements JParser, JJSONMockModelParser{
	
	@Override
	public JMockModel parse(JDefaultMethodMeta methodMeta,JMockContext context){
		JMockModel mockModel=new JMockModel();
		Annotation[] annotations=methodMeta.getAnnotations();
		JMockSkip mockSkip=null;
		if(annotations!=null&&annotations.length>0){
			for(Annotation annotation:annotations){
				if(JMockSkip.class.isInstance(annotation)){
					mockSkip=(JMockSkip)annotation;
				}
				else if(JJSONMock.class.isInstance(annotation)){
					JJSONMock jsonMock=(JJSONMock)annotation;
					mockModel.setData(jsonMock.data());
					mockModel.setUri(jsonMock.uri());
					mockModel.setMock(jsonMock.mock());
				}
			}
			if(mockSkip!=null){
				mockModel.setMock(false);
			}
		}
		return mockModel;
	}
	
}
