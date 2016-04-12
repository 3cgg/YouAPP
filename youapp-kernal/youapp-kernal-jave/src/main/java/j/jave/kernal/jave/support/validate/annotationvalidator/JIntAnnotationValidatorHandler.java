package j.jave.kernal.jave.support.validate.annotationvalidator;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.support.dataconvert.JDataConvertor;
import j.jave.kernal.jave.support.validate.JValidatingException;
import j.jave.kernal.jave.support.validate.annotationvalidator.annotation.JInt;

import java.lang.reflect.Field;

public class JIntAnnotationValidatorHandler implements JPropertyAnnotationValidatorHandler {

	@Override
	public boolean accept(Field field, Object object) {
		boolean needValidating=false;
		JInt annotation=field.getAnnotation(JInt.class);
		needValidating=needValidating||(annotation!=null&&annotation.active());
		return needValidating;
	}
	
	private JDataConvertor dataConvertor=JDataConvertor.build(JConfiguration.get());
	
	@Override
	public Object handle(Field field, Object object) {
		try{
			JInt annotation=field.getAnnotation(JInt.class);
			int min=annotation.min();
			int max=annotation.max();
			Object value=field.get(object);
			if(value!=null){
				int val=-1;
				try {
					val = dataConvertor.convert(Integer.class, value).intValue();
				} catch (NumberFormatException e) {
					throw new JValidatingException(
							"field["+ field.getName() + "] in the class["+ object.getClass() + "] value is not "
							+ int.class);
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
