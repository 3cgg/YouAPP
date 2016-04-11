package j.jave.kernal.jave.support.databind.proext;

import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support.databind.proext.annotation.DateFormatExtend;
import j.jave.kernal.jave.utils.JDateUtils;

import java.lang.reflect.Field;
import java.util.Date;

public class DateFormatExtendHandler implements PropertyExtendHandler {
	
	@Override
	public boolean accept(Field field, Object object) {
		boolean needPropertyExtend=false;
		DateFormatExtend dateFormatExtend=field.getAnnotation(DateFormatExtend.class);
		needPropertyExtend=needPropertyExtend||(dateFormatExtend!=null&&dateFormatExtend.active());
		return needPropertyExtend;
	}
	
	@Override
	public void handle(Field field, Object object) {
		DateFormatExtend dateFormatExtend=field.getAnnotation(DateFormatExtend.class);
		String property=dateFormatExtend.property();
		Date date=(Date) JClassUtils.getByField(property, object, false);
		String pattern=dateFormatExtend.pattern();
		Object dateString=JDateUtils.format(date, pattern);
		JClassUtils.setOnField(field, dateString, object);
	}
	
}
