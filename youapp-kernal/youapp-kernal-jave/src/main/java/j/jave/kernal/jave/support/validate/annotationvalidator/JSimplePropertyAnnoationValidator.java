package j.jave.kernal.jave.support.validate.annotationvalidator;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support.propertyannotaion.JPropertyAnnotationConfig;
import j.jave.kernal.jave.support.propertyannotaion.JPropertyAnnotationProcessor;
import j.jave.kernal.jave.support.validate.JValidatable;
import j.jave.kernal.jave.support.validate.JValidatingException;
import j.jave.kernal.jave.support.validate.JValidator;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		config.setClassIdentifiers(Arrays.asList(JValidatable.class));
		propertyAnnotationProcessor=JPropertyAnnotationProcessor.build(config);
	}
	
}
