package j.jave.kernal.jave.reflect;

import j.jave.kernal.jave.utils.JCollectionUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * THE CLASS do some functions that contains something related reflection. 
 * @author Administrator
 *
 */
public abstract class JReflectionUtils {
	
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
		targetMethod.setAccessible(true);
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
		targetMethod.setAccessible(true);
		return targetMethod.invoke(object, parameters);
	}
	
	
	/**
	 * Make the given method accessible, explicitly setting it accessible if
	 * necessary. The {@code setAccessible(true)} method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * @param method the method to make accessible
	 * @see java.lang.reflect.Method#setAccessible
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) ||
				!Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
			method.setAccessible(true);
		}
	}
	
	
	/**
	 * Determine whether the given method is an "equals" method.
	 * @see java.lang.Object#equals(Object)
	 */
	public static boolean isEqualsMethod(Method method) {
		if (method == null || !method.getName().equals("equals")) {
			return false;
		}
		Class<?>[] paramTypes = method.getParameterTypes();
		return (paramTypes.length == 1 && paramTypes[0] == Object.class);
	}

	/**
	 * Determine whether the given method is a "hashCode" method.
	 * @see java.lang.Object#hashCode()
	 */
	public static boolean isHashCodeMethod(Method method) {
		return (method != null && method.getName().equals("hashCode") && method.getParameterTypes().length == 0);
	}
	
	/**
	 * Adapt the given arguments to the target signature in the given method,
	 * if necessary: in particular, if a given vararg argument array does not
	 * match the array type of the declared vararg parameter in the method.
	 * @param method the target method
	 * @param arguments the given arguments
	 * @return a cloned argument array, or the original if no adaptation is needed
	 */
	public static Object[] adaptArgumentsIfNecessary(Method method, Object... arguments) {
		if (method.isVarArgs() && JCollectionUtils.hasInArray(arguments)) {
			Class<?>[] paramTypes = method.getParameterTypes();
			if (paramTypes.length == arguments.length) {
				int varargIndex = paramTypes.length - 1;
				Class<?> varargType = paramTypes[varargIndex];
				if (varargType.isArray()) {
					Object varargArray = arguments[varargIndex];
					if (varargArray instanceof Object[] && !varargType.isInstance(varargArray)) {
						Object[] newArguments = new Object[arguments.length];
						System.arraycopy(arguments, 0, newArguments, 0, varargIndex);
						Class<?> targetElementType = varargType.getComponentType();
						int varargLength = Array.getLength(varargArray);
						Object newVarargArray = Array.newInstance(targetElementType, varargLength);
						System.arraycopy(varargArray, 0, newVarargArray, 0, varargLength);
						newArguments[varargIndex] = newVarargArray;
						return newArguments;
					}
				}
			}
		}
		return arguments;
	}
}
