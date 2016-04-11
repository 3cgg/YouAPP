package j.jave.kernal.jave.support.databind.proext;

import j.jave.kernal.jave.identifier.DataBinder;

import java.lang.reflect.Field;

public interface PropertyExtendHandler extends DataBinder {
	
	public abstract boolean accept(Field field,Object object);
	
	public abstract void handle(Field field,Object object);
	
}
