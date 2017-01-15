package me.bunny.kernel.jave.support.validate.annotationvalidator;

import java.lang.reflect.Field;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.jave.support.parser.JDefaultSimpleDataParser;
import me.bunny.kernel.jave.support.validate.JValidatingException;
import me.bunny.kernel.jave.support.validate.annotationvalidator.annotation.JByte;

public class JByteAnnotationValidatorHandler implements JPropertyAnnotationValidatorHandler {

	@Override
	public boolean accept(Field field, Object object) {
		boolean needValidating=false;
		JByte annotation=field.getAnnotation(JByte.class);
		needValidating=needValidating||(annotation!=null&&annotation.active());
		return needValidating;
	}

	private JDefaultSimpleDataParser dataParser=JDefaultSimpleDataParser.build(JConfiguration.get());
	
	
	@Override
	public Object handle(Field field, Object object) {
		try{
			JByte annotation=field.getAnnotation(JByte.class);
			byte min=annotation.min();
			byte max=annotation.max();
			Object value=field.get(object);
			if(value!=null){
				byte val=-1;
				try {
					val = dataParser.parse(Byte.class, value).byteValue();
				} catch (NumberFormatException e) {
					throw new JValidatingException(
							"field["+ field.getName() + "] in the class["+ object.getClass() + "] value is not "
							+ byte.class);
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
