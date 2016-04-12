package j.jave.kernal.jave.support.validate.annotationvalidator;

import j.jave.kernal.jave.support.validate.JValidatingException;
import j.jave.kernal.jave.support.validate.annotationvalidator.annotation.JNotEmpty;
import j.jave.kernal.jave.utils.JStringUtils;

import java.lang.reflect.Field;

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
