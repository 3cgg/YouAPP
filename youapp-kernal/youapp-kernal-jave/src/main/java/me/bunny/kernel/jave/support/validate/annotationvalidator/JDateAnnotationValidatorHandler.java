package me.bunny.kernel.jave.support.validate.annotationvalidator;

import java.lang.reflect.Field;
import java.util.Date;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.jave.support.parser.JDefaultSimpleDataParser;
import me.bunny.kernel.jave.support.validate.JValidatingException;
import me.bunny.kernel.jave.support.validate.annotationvalidator.annotation.JDate;

public class JDateAnnotationValidatorHandler implements JPropertyAnnotationValidatorHandler {

	@Override
	public boolean accept(Field field, Object object) {
		boolean needValidating=false;
		JDate annotation=field.getAnnotation(JDate.class);
		needValidating=needValidating||(annotation!=null&&annotation.active());
		return needValidating;
	}

	
	private JDefaultSimpleDataParser dataParser=JDefaultSimpleDataParser.build(JConfiguration.get());
	
	@Override
	public Object handle(Field field, Object object) {
		try{
			JDate annotation=field.getAnnotation(JDate.class);
			long min=annotation.min();
			long max=annotation.max();
			Object value=field.get(object);
			if(value!=null){
				long val=-1;
				try {
					val = dataParser.parse(Date.class, value).getTime();
				} catch (Exception e) {
					throw new JValidatingException(
							"field["+ field.getName() + "] in the class["+ object.getClass() + "] value is not date.");
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
