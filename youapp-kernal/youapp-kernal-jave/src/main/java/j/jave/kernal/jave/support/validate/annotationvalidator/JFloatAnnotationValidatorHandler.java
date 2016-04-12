package j.jave.kernal.jave.support.validate.annotationvalidator;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.support.dataconvert.JDataConvertor;
import j.jave.kernal.jave.support.validate.JValidatingException;
import j.jave.kernal.jave.support.validate.annotationvalidator.annotation.JFloat;

import java.lang.reflect.Field;

public class JFloatAnnotationValidatorHandler implements JPropertyAnnotationValidatorHandler {

	@Override
	public boolean accept(Field field, Object object) {
		boolean needValidating=false;
		JFloat annotation=field.getAnnotation(JFloat.class);
		needValidating=needValidating||(annotation!=null&&annotation.active());
		return needValidating;
	}

	private JDataConvertor dataConvertor=JDataConvertor.build(JConfiguration.get());
	
	@Override
	public Object handle(Field field, Object object) {
		try{
			JFloat annotation=field.getAnnotation(JFloat.class);
			float min=annotation.min();
			float max=annotation.max();
			Object value=field.get(object);
			if(value!=null){
				float val=-1;
				try {
					val = dataConvertor.convert(Float.class, value).floatValue();
				} catch (NumberFormatException e) {
					throw new JValidatingException(
							"field["+ field.getName() + "] in the class["+ object.getClass() + "] value is not "
							+ float.class);
				}
				if(val>max||val<min){
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
