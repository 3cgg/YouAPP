package me.bunny.kernel._c.support._package;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import me.bunny.kernel._c.support._package.JAbstractFieldFinder.JFieldFilter;

public class JDefaultFieldFilter implements JFieldFilter {

	@Override
	public boolean filter(Field field, Class<?> classIncudeField) {
		return false;
	}

	@Override
	public boolean filter(Class<?> clazz) {
		return false;
	}

	@Override
	public int[] fieldModifiers() {
		return new int[]{Modifier.PUBLIC,Modifier.PROTECTED,Modifier.PRIVATE};
	}
	
}
