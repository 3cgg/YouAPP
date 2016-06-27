package j.jave.kernal.jave.model.support.detect;

import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.kernal.jave.support._package.JAbstractFieldFinder.JFieldFilter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
