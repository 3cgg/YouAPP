package j.jave.kernal.jave.support.validate.annotationvalidator;

import j.jave.kernal.jave.support.validate.JValidatingException;
import j.jave.kernal.jave.support.validate.annotationvalidator.annotation.JLength;

import java.lang.reflect.Field;

public class JLengthAnnotationValidatorHandler implements JPropertyAnnotationValidatorHandler {

	@Override
	public boolean accept(Field field, Object object) {
		boolean needValidating=false;
		JLength annotation=field.getAnnotation(JLength.class);
		needValidating=needValidating||(annotation!=null&&annotation.active());
		return needValidating;
	}
	
	@Override
	public Object handle(Field field, Object object) {
		try{
			JLength annotation=field.getAnnotation(JLength.class);
			int min=annotation.min();
			int max=annotation.max();
			Object value=field.get(object);
			if(value!=null){
				if(!String.class.isInstance(value)){
					throw new JValidatingException(
							"field["+ field.getName() + "] in the class["+ object.getClass() + "] value is not string.");
				}
				String val=(String) value;
				if(val.length()>max||val.length()<min){
					throw new JValidatingException(
							"field["+field.getName()+"] in the class["+object.getClass()+"] value is not between "
							+ min+" and "+max);
				}
			}
			return true;
		}catch(Exception e){
			JValidatingException.throwException(e);
		}
		return true;
	}

}
