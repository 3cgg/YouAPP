package me.bunny.kernel.jave.support.databind.proext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.JProperties;
import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.support.propertyannotaion.JPropertyAnnotationConfig;
import me.bunny.kernel.jave.support.propertyannotaion.JPropertyAnnotationProcessor;
import me.bunny.kernel.jave.utils.JStringUtils;

/**
 * only process whose classes that implement the {@link JPropertyExtendable} .
 * @author J
 * @see JPropertyExtendable
 */
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
		config.setClassIdentifiers((List)Arrays.asList(JPropertyExtendable.class));
		propertyAnnotationProcessor=JPropertyAnnotationProcessor.build(config);
	}
	
}
