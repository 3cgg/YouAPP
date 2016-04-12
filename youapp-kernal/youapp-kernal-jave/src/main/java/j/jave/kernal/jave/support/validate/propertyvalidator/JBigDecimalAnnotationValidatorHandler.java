package j.jave.kernal.jave.support.validate.propertyvalidator;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.support.convert.JDataConvertor;
import j.jave.kernal.jave.support.validate.propertyvalidator.annotation.JBigDecimal;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class JBigDecimalAnnotationValidatorHandler implements JPropertyAnnotationValidatorHandler {

	@Override
	public boolean accept(Field field, Object object) {
		boolean needValidating=false;
		JBigDecimal annotation=field.getAnnotation(JBigDecimal.class);
		needValidating=needValidating||(annotation!=null&&annotation.active());
		return needValidating;
	}

	private JDataConvertor dataConvertor=JDataConvertor.build(JConfiguration.get());
	
	
	@Override
	public Object handle(Field field, Object object) {
		try{
			JBigDecimal annotation=field.getAnnotation(JBigDecimal.class);
			String min=annotation.min();
			String max=annotation.max();
			Object value=field.get(object);
			if(value!=null){
				BigDecimal val=null;
				try {
					val = dataConvertor.convert(BigDecimal.class, value);
				} catch (Exception e) {
					throw new JValidatingException(
							"field["+ field.getName() + "] in the class["+ object.getClass() + "] value is not "
							+ BigDecimal.class);
				}
				BigDecimal minVal=new BigDecimal(min);
				BigDecimal maxVal=new BigDecimal(max);
				if(val.compareTo(maxVal)>0
						||val.compareTo(minVal)<0
						){
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
