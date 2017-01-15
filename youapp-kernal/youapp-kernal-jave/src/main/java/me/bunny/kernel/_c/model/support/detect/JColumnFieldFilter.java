package me.bunny.kernel._c.model.support.detect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import me.bunny.kernel._c.model.support.JColumn;
import me.bunny.kernel._c.model.support.JTable;
import me.bunny.kernel._c.support._package.JAbstractFieldFinder.JFieldFilter;

public class JColumnFieldFilter implements JFieldFilter{

	@Override
	public boolean filter(Field field, Class<?> classIncudeField) {
		return field.getAnnotation(JColumn.class)==null;
	}

	@Override
	public boolean filter(Class<?> clazz) {
		return clazz.getAnnotation(JTable.class)==null;
	}

	@Override
	public int[] fieldModifiers() {
		return new int[]{Modifier.PUBLIC,Modifier.PROTECTED,Modifier.PRIVATE};
	}

}
