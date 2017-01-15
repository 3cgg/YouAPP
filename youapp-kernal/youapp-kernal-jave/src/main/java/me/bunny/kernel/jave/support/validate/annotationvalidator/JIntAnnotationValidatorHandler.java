package me.bunny.kernel.jave.support.validate.annotationvalidator;

import java.lang.reflect.Field;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.jave.support.parser.JDefaultSimpleDataParser;
import me.bunny.kernel.jave.support.validate.JValidatingException;
import me.bunny.kernel.jave.support.validate.annotationvalidator.annotation.JInt;

public class JIntAnnotationValidatorHandler implements JPropertyAnnotationValidatorHandler {

	@Override
	public boolean accept(Field field, Object object) {
		boolean needValidating=false;
		JInt annotation=field.getAnnotation(JInt.class);
		needValidating=needValidating||(annotation!=null&&annotation.active());
		return needValidating;
	}
	
	private JDefaultSimpleDataParser dataParser=JDefaultSimpleDataParser.build(JConfiguration.get());
	
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
					val = dataParser.parse(Integer.class, value).intValue();
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
