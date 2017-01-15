package me.bunny.kernel._c.support.validate.annotationvalidator;

import java.lang.reflect.Field;

import me.bunny.kernel._c.support.validate.JValidatingException;
import me.bunny.kernel._c.support.validate.annotationvalidator.annotation.JNotEmpty;
import me.bunny.kernel._c.utils.JStringUtils;

public class JNotEmptyAnnotationValidatorHandler implements JPropertyAnnotationValidatorHandler {

	@Override
	public boolean accept(Field field, Object object) {
		boolean needValidating=false;
		JNotEmpty annotation=field.getAnnotation(JNotEmpty.class);
		needValidating=needValidating||(annotation!=null&&annotation.active());
		return needValidating;
	}
	
	@Override
	public Object handle(Field field, Object object) {
		try{
			Object value=field.get(object);
			if(!JStringUtils.isNotNullOrEmpty((String) value)){
				throw new JValidatingException(
						"field["+ field.getName() + "] in the class["+ object.getClass() + "] value is empty. ");
			}
			return true;
		}catch(Exception e){
			JValidatingException.throwException(e);
		}
		return true;
	}

}
