package me.bunny.kernel.jave.support.validate.annotationvalidator;

import java.lang.reflect.Field;

import me.bunny.kernel.jave.support.validate.JValidatingException;
import me.bunny.kernel.jave.support.validate.annotationvalidator.annotation.JNotNull;

public class JNotNullAnnotationValidatorHandler implements JPropertyAnnotationValidatorHandler {

	@Override
	public boolean accept(Field field, Object object) {
		boolean needValidating=false;
		JNotNull annotation=field.getAnnotation(JNotNull.class);
		needValidating=needValidating||(annotation!=null&&annotation.active());
		return needValidating;
	}
	
	@Override
	public Object handle(Field field, Object object) {
		try{
			Object value=field.get(object);
			if(value==null){
				throw new JValidatingException(
						"field["+ field.getName() + "] in the class["+ object.getClass() + "] value is null. ");
			}
			return true;
		}catch(Exception e){
			JValidatingException.throwException(e);
		}
		return true;
	}

}
