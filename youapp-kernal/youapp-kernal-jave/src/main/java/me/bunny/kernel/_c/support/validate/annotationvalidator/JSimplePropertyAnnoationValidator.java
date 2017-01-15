package me.bunny.kernel._c.support.validate.annotationvalidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.JProperties;
import me.bunny.kernel._c.reflect.JClassUtils;
import me.bunny.kernel._c.support.propertyannotaion.JPropertyAnnotationConfig;
import me.bunny.kernel._c.support.propertyannotaion.JPropertyAnnotationProcessor;
import me.bunny.kernel._c.support.validate.JValidatable;
import me.bunny.kernel._c.support.validate.JValidatingException;
import me.bunny.kernel._c.support.validate.JValidator;
import me.bunny.kernel._c.utils.JStringUtils;

public class JSimplePropertyAnnoationValidator<T> implements JValidator<T>{
	
	@Override
	public boolean validate(T object) {
		try{
			propertyAnnotationProcessor.handle(object);
		}catch(JValidatingException e){
			return false;
		}
		return true;
	}
	
	public void bind(Object object) {
		propertyAnnotationProcessor.handle(object);
	}
	
	private static JPropertyAnnotationProcessor propertyAnnotationProcessor;
	
	static{
		List<JPropertyAnnotationValidatorHandler> propertyAnnotationValidatorHandlers=new ArrayList<JPropertyAnnotationValidatorHandler>();
		propertyAnnotationValidatorHandlers.add(new JByteAnnotationValidatorHandler());
		propertyAnnotationValidatorHandlers.add(new JIntAnnotationValidatorHandler());
		propertyAnnotationValidatorHandlers.add(new JFloatAnnotationValidatorHandler());
		propertyAnnotationValidatorHandlers.add(new JDoubleAnnotationValidatorHandler());
		propertyAnnotationValidatorHandlers.add(new JBigDecimalAnnotationValidatorHandler());
		propertyAnnotationValidatorHandlers.add(new JDateAnnotationValidatorHandler());
		propertyAnnotationValidatorHandlers.add(new JNotNullAnnotationValidatorHandler());
		propertyAnnotationValidatorHandlers.add(new JNotEmptyAnnotationValidatorHandler());
		propertyAnnotationValidatorHandlers.add(new JLengthAnnotationValidatorHandler());
		
		String proExtClassesString=JConfiguration.get().getString(JProperties.DATAVALIDATE_PROPERTY_ANNOTATION_HANDLER, JNotNullAnnotationValidatorHandler.class.getName());
		String[] proExtClassStrings= JStringUtils.toStringArray(proExtClassesString, ",");
		for(String proExtClassString:proExtClassStrings){
			Class<?> clazz=JClassUtils.load(proExtClassString.trim());
			boolean exists=false;
			for(JPropertyAnnotationValidatorHandler propertyExtendHandler:propertyAnnotationValidatorHandlers){
				if(clazz==propertyExtendHandler.getClass()){
					exists=true;
					break;
				}
			}
			if(!exists){
				propertyAnnotationValidatorHandlers.add((JPropertyAnnotationValidatorHandler) JClassUtils.newObject(clazz));
			}
		}
		JPropertyAnnotationConfig config=new JPropertyAnnotationConfig();
		config.setPropertyAnnotationHandlers(propertyAnnotationValidatorHandlers);
		config.setClassIdentifiers((List)Arrays.asList(JValidatable.class));
		propertyAnnotationProcessor=JPropertyAnnotationProcessor.build(config);
	}
	
}
