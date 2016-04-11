package j.jave.kernal.jave.support.databind.proext;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support.propertyannotaion.JPropertyAnnotationConfig;
import j.jave.kernal.jave.support.propertyannotaion.JPropertyAnnotationProcessor;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSimplePropertyExtendBinder implements JPropertyExtendBinder{
	
	public void bind(Object object) {
		propertyAnnotationProcessor.handle(object);
	}
	
	private static JPropertyAnnotationProcessor propertyAnnotationProcessor;
	
	static{
		List<JPropertyExtendHandler> propertyExtendHandlers=new ArrayList<JPropertyExtendHandler>();
		propertyExtendHandlers.add(new JDateFormatExtendHandler());
		String proExtClassesString=JConfiguration.get().getString(JProperties.DATABIND_PROPERTY_EXT_HANDLER, JDateFormatExtendHandler.class.getName());
		String[] proExtClassStrings= JStringUtils.toStringArray(proExtClassesString, ",");
		for(String proExtClassString:proExtClassStrings){
			Class<?> clazz=JClassUtils.load(proExtClassString.trim());
			boolean exists=false;
			for(JPropertyExtendHandler propertyExtendHandler:propertyExtendHandlers){
				if(clazz==propertyExtendHandler.getClass()){
					exists=true;
					break;
				}
			}
			if(!exists){
				propertyExtendHandlers.add((JPropertyExtendHandler) JClassUtils.newObject(clazz));
			}
		}
		JPropertyAnnotationConfig config=new JPropertyAnnotationConfig();
		config.setPropertyAnnotationHandlers(propertyExtendHandlers);
		config.setClassIdentifiers(Arrays.asList(JPropertyExtendable.class));
		propertyAnnotationProcessor=JPropertyAnnotationProcessor.build(config);
	}
	
}
