package j.jave.kernal.jave.support.propertyannotaion;

import java.lang.reflect.Field;

public interface JPropertyAnnotationHandler {
	
	public abstract boolean accept(Field field,Object object);
	
	public abstract Object handle(Field field,Object object);
	
}
