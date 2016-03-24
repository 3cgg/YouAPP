package j.jave.kernal.jave.support.detect;

import j.jave.kernal.jave.support.detect.JMethodDetector.JMethodFilter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class JDefaultMethodFilter implements JMethodFilter{

	@Override
	public boolean filter(Method method, Class<?> classIncudeMethod) {
		return false;
	}
	
	public boolean filter(java.lang.Class<?> clazz) {
		return false;
	};
	
	@Override
	public int[] methodModifiers() {
		return new int[]{Modifier.PUBLIC,Modifier.PROTECTED,Modifier.PRIVATE};
	}
	
}
