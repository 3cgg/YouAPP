package me.bunny.kernel._c.support.databind.proext;

import java.lang.reflect.Field;
import java.util.Date;

import me.bunny.kernel._c.reflect.JClassUtils;
import me.bunny.kernel._c.support.databind.proext.annotation.DateFormatExtend;
import me.bunny.kernel._c.utils.JDateUtils;

public class JDateFormatExtendHandler implements JPropertyExtendHandler {
	
	@Override
	public boolean accept(Field field, Object object) {
		boolean needPropertyExtend=false;
		DateFormatExtend dateFormatExtend=field.getAnnotation(DateFormatExtend.class);
		needPropertyExtend=needPropertyExtend||(dateFormatExtend!=null&&dateFormatExtend.active());
		return needPropertyExtend;
	}
	
	@Override
	public Object handle(Field field, Object object) {
		DateFormatExtend dateFormatExtend=field.getAnnotation(DateFormatExtend.class);
		String property=dateFormatExtend.property();
		Date date=(Date) JClassUtils.getByField(property, object, false);
		String pattern=dateFormatExtend.pattern();
		Object dateString=JDateUtils.format(date, pattern);
		JClassUtils.setOnField(field, dateString, object);
		return null;
	}
	
}
