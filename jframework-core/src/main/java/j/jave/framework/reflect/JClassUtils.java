/**
 * 
 */
package j.jave.framework.reflect;

import j.jave.framework.utils.JUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * utils collection for relfecting class. 
 * @author J
 */
public abstract class JClassUtils {

	/**
	 * get value from object via calling getter method. 
	 * @param property
	 * @param model
	 * @return
	 */
	public static Object get(String property, Object model) {
		try {
			Class clazz = model.getClass();
			Method method=null;
			while (clazz != null) {
				try {
					Field field=getField(property, model);
					String getterName=getterName(property, field.getType() ==boolean.class);
					method = clazz.getDeclaredMethod(getterName);
				} catch (NoSuchMethodException e) {
					clazz = clazz.getSuperclass();
					continue;
				}
				if (method != null) {
					return method.invoke(model);
				}
				clazz = clazz.getSuperclass();
			}

			if(method==null){
				throw new RuntimeException("cannot find getter method for property "+property);
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * all properties
	 * @param property
	 * @return
	 */
	public static String getterName(String property) {
		if (StringUtils.isNotEmpty(property)) {
			
			if(property.length()>1){
				String second=""+property.charAt(1);
				if(Pattern.matches("[A-Z]", second)){
					return "get"+property;
				}
				else{
					return "get"+property.substring(0, 1).toUpperCase()
							+ property.substring(1);
				}
			}
			else{
				return  "get" + property.toUpperCase();
			}
		}
		return null;
	}
	
	
	
	/**
	 * only for property that is not boolean type.
	 * @param property
	 * @return
	 */
	public static String getterName(String property,boolean isBoolean) {
		
		if(!isBoolean){
			return getterName(property);
		}
		String regex="is[A-Z][a-zA-Z_]*";
		if(Pattern.matches(regex, property)){
			// start with is[A-Z]
			return property;
		}
		else{
			if(property.length()>1){
				String second=""+property.charAt(1);
				if(Pattern.matches("[A-Z]", second)){
					return "is"+property;
				}
				else{
					return "is"+property.substring(0, 1).toUpperCase()
							+ property.substring(1);
				}
			}
			else{
				return "is"+property.toLowerCase();
			}
			
		}
	}
	
	
	/**
	 * all properties 
	 * @param property
	 * @return
	 */
	public static String setterName(String property,boolean isBoolean) {
		
		if(!isBoolean){
			return setterName(property);
		}
		String regex="is[A-Z][a-zA-Z_]*";
		if(Pattern.matches(regex, property)){
			// start with is[A-Z]
			return "set"+property.substring(2);
		}
		else{
			if(property.length()>1){
				String second=""+property.charAt(1);
				if(Pattern.matches("[A-Z]", second)){
					return "set"+property;
				}
				else{
					return "set"+property.substring(0, 1).toUpperCase()
							+ property.substring(1);
				}
			}
			else{
				return "set"+property.toLowerCase();
			}
			
		}
	}
	
	/**
	 * set property value via calling setter method.
	 * @param property
	 * @param value
	 * @param model
	 * @return
	 */
	public static String set(String property,Object value,Object model) {
		try {
			Class clazz = model.getClass();
			Method method=null;
			while (clazz != null) {
				try {
					Field field=getField(property, model);
					String setterName=setterName(property, field.getType() ==boolean.class);
					
					if(value!=null){
						method = clazz.getDeclaredMethod(setterName,value.getClass());
					}
					else{
						Method[] methods=clazz.getDeclaredMethods();
						if(JUtils.hasInArray(methods)){
							for (int i = 0; i < methods.length; i++) {
								Method inner=methods[i];
								if(setterName.equals(inner.getName())){
									method=inner;
								}

							}
						}
						if(method==null){
							throw new NoSuchMethodException(setterName);
						}
					}


				} catch (NoSuchMethodException e) {
					clazz = clazz.getSuperclass();
					continue;
				}
				if (method != null) {
					Object obj = method.invoke(model, value);
					if (obj == null) {
						return null;
					} else {
						return String.valueOf(obj);
					}
				}
				clazz = clazz.getSuperclass();
			}

			if(method==null){
				throw new RuntimeException("cannot find setter method for property "+property);
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * get field. 
	 * @param property
	 * @param model
	 * @return
	 */
	public static Field getField(String property , Object model){
		if(model==null) return null;
		if(JUtils.isNullOrEmpty(property)) return null;
		Field field=null;
		Class superClass=model.getClass();
		while(superClass!=null){
			try {
				field=superClass.getDeclaredField(property);
			} catch (NoSuchFieldException e) {
				continue;
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			}
			superClass=superClass.getSuperclass();
		}
		return field;
	}
	
	/**
	 * for all property whether or not it is boolean type.
	 *
	 * @param property
	 * @return
	 */
	public static String setterName(String property) {
		if (StringUtils.isNotEmpty(property)) {
			if(property.length()>1){
				String second=""+property.charAt(1);
				if(Pattern.matches("[A-Z]", second)){
					return "set"+property;
				}
				else{
					return "set"+property.substring(0, 1).toUpperCase()
							+ property.substring(1);
				}
			}
			else{
				return  "set" + property.toUpperCase();
			}
		}
		return null;
	}
	
	
}
