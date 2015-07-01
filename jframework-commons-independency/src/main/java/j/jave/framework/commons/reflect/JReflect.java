package j.jave.framework.commons.reflect;

import java.lang.reflect.Method;


/**
 * THE CLASS do some functions that contains something related reflection. 
 * @author Administrator
 *
 */
public abstract class JReflect {
	
	/**
	 * invoke the method related object, ability of  holding all member method, i.e. public,private ...
	 * And also scan up to the parent class to find out the specified method of the passed method name.
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
	
	
	/**
	 * invoke the method that whose method parameter type, only hold the specified public member method.
	 * @param object
	 * @param methodName
	 * @param parameterTypes  the order is the same as @param parameters.
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public static Object invoke(Object object, String methodName,Class<?>[] parameterTypes,Object[] parameters) throws Exception {
		Class<?> clazz=object.getClass();
		Method targetMethod=clazz.getMethod(methodName, parameterTypes);
		if(targetMethod==null) throw new RuntimeException("Method ["+methodName+"] not found.");			
		return targetMethod.invoke(object, parameters);
	}
	

}
