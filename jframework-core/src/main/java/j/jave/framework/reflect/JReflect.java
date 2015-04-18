package j.jave.framework.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * THE CLASS do some functions that contains something related reflection. 
 * @author Administrator
 *
 */
public class JReflect {
	
	/**
	 * invoke the method related object. 
	 * @param object
	 * @param methodName
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public static Object invoke(Object object, String methodName,Object[] parameters) throws Exception {
		Class<?> clazz=object.getClass();
		Method targetMethod=null;
		while(clazz!=null){
			Method[] methods=clazz.getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method=methods[i];
				if(!method.getName().equals(methodName)) continue;
				Class<?>[] parameterTypes=method.getParameterTypes();
				if(parameterTypes.length==parameters.length){
					boolean isMatchAll=true;
					for (int j = 0; j < parameterTypes.length; j++) {
						if(!parameterTypes[j].isInstance(parameters[j])){
							isMatchAll=false;
							break;
						}
					}
					if(isMatchAll){
						targetMethod=method;
						break;
					}
				}
				else{
					continue; 
				}
			}
			if(targetMethod!=null) break;
			clazz=clazz.getSuperclass();
		}
		if(targetMethod==null) throw new RuntimeException("Method ["+methodName+"] not found.");			
		return targetMethod.invoke(object, parameters);
	}
	
	public static boolean isNewInstanceable(Class<?> clazz){
		int modify=clazz.getModifiers();
		return !Modifier.isAbstract(modify)&&!Modifier.isInterface(modify)&&!Modifier.isPrivate(modify);
	}
	
	public static boolean isAccessable(Field field){
		int modify=field.getModifiers();
		return !Modifier.isPrivate(modify)&&!Modifier.isProtected(modify);
	}
	
	/**
	 * resolve the type of property "propertyName" in the object . 
	 * @param object
	 * @param propertyName
	 * @return
	 */
	public static Class<?> getType(Object object,String propertyName){
		try {
			Class<?> clazz=object.getClass();
			Field field=null;
			while(clazz!=null){
				try{
					field=clazz.getDeclaredField(propertyName);
					if(field!=null){
						break;
					}
				}catch(NoSuchFieldException e ){
					clazz=clazz.getSuperclass();
				}
			}
			
			if(field!=null){
				return field.getType();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return null;
	}
	
	
	
	
	
	
	
	
}
