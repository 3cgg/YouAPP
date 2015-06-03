/**
 * 
 */
package j.jave.framework.reflect;

import j.jave.framework.utils.JCollectionUtils;
import j.jave.framework.utils.JDateUtils;
import j.jave.framework.utils.JNumberUtils;
import j.jave.framework.utils.JStringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
						if(JCollectionUtils.hasInArray(methods)){
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
		if(JStringUtils.isNullOrEmpty(property)) return null;
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
	
	/**
	 * get all methods , includes those methods in the {@link Object}
	 * @param clazz
	 * @param deep
	 * @return
	 */
	public static List<Method> getMethods(Class<?> clazz,boolean deep,int... modifiers){
		List<Method> methods=new ArrayList<Method>();
		Class<?> superClass=clazz;
		
		if(modifiers!=null&&modifiers.length==0&&modifiers[0]==Modifier.PUBLIC){
			if(deep){
				Collections.addAll(methods, superClass.getMethods());
				return methods;
			}
		}
		
		while(superClass!=null){
			Method[] meds=superClass.getDeclaredMethods();
			for(int i=0;i<meds.length;i++){
				Method method=meds[i];
				boolean exists=false;
				if(modifiers!=null&&modifiers.length>0){
					for(int mdf=0;mdf<modifiers.length;mdf++){
						if(method.getModifiers()==modifiers[mdf]){
							exists=true;
							break;
						}
					}
				}
				
				if(exists){
					methods.add(method);
				}
			}
			
			if(deep){ // deep scanning
				superClass=superClass.getSuperclass();
			}
			else{
				superClass=null; // break;
			}
		}
		return methods;
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
	
	/**
	 * {@link Modifier} NOT ABSTRACT,INTERFACE OR PRIVATE.
	 * @param clazz
	 * @return
	 */
	public static boolean isNewInstanceable(Class<?> clazz){
		int modify=clazz.getModifiers();
		return !Modifier.isAbstract(modify)&&!Modifier.isInterface(modify)&&!Modifier.isPrivate(modify);
	}
	
	/**
	 *  {@link Modifier} NOT PROTECTED OR PRIVATE.
	 * @param field
	 * @return
	 */
	public static boolean isAccessable(Field field){
		int modify=field.getModifiers();
		return !Modifier.isPrivate(modify)&&!Modifier.isProtected(modify);
	}
	
	
	/**
	 * scan all property in the {@param obj} to match the first property name.
	 * set {@code valueObject} to target object {@code obj } via the direction of properties link by "."  , i.e. "user.userName","user.password".
	 * <p>note: any list property in any property not supported. 
	 * @param obj target object.
	 * @param nameLink  link by "."  , i.e. "user.userName","user.password".
	 * @param valueObject  value set 
	 * @throws Exception
	 */
	public static void set(Object obj,String nameLink,Object valueObject) throws Exception{
		
		if(nameLink.indexOf(".")==-1 ) return ;  // no need fill in automatically 
		
		String[] names=new String[]{nameLink};
		if(nameLink.indexOf(".")!=-1){
			names=nameLink.split("[.]");
		}
		Object target=obj; 
		for (int i = 0; i < names.length; i++) {
			String name=names[i];
			Class<?> superClass=target.getClass();
			Field field=null;
			while(superClass!=null&&field==null){
				try{
					field=superClass.getDeclaredField(name);
				}catch(NoSuchFieldException e){
					superClass=superClass.getSuperclass();
				}
			}
			
			if(field==null){
				throw new RuntimeException("["+name+"] attribute not found in "+target.getClass().getName());
			}
			
			field.setAccessible(true);
			if(i==names.length-1){  // the last one .  set value
				if(List.class.isInstance(valueObject)){ 
					field.set(target, valueObject);
				}
				else{
					String value=String.valueOf(valueObject);
					if(field.getType()==String.class){
						field.set(target, value);
					}
					else if(field.getType()==Double.class||field.getType()==double.class){
						field.set(target, JNumberUtils.toDouble(value));
					}
					else if(field.getType()==Integer.class||field.getType()==int.class){
						field.set(target, JNumberUtils.toInt(value));
					}
					else if(field.getType()==Long.class||field.getType()==long.class){
						field.set(target, JNumberUtils.toLong(value));
					}
					else if(field.getType()==Timestamp.class){
						if(JStringUtils.isNotNullOrEmpty(value)){
							field.set(target, JDateUtils.parseTimestampWithSeconds(value));
						}
					}
					else if(field.getType()==Date.class){
						if(JStringUtils.isNotNullOrEmpty(value)){
							field.set(target, JDateUtils.parseDate(value));
						}
					}
				}
			}
			else{  
				Object attributeObject=field.get(target);
				if(attributeObject==null){
					Object temp=field.getType().newInstance();
					field.set(target, temp);
					target=temp;
				}
				else{
					target=attributeObject;
				}
			}
		}
	}
	
	
	/**
	 * get all interfaces extended by the class or super class. 
	 * @param object on the parameter go through all extended interfaces.
	 * @param superInterface Determines if the class or interface represented by this param {@code superInterface}
	 * is either a superclass or superinterface of, the class or interface represented by the specified interfaces extended by the param {@code object}
	 * @return  empty returned , if no any extended interfaces 
	 */
	public static List<Class<?>> getAllInterfaces(Object object,Class<?> superInterface){
		return getAllInterfaces(object.getClass(), superInterface);
	}
	
	/**
	 * get all interfaces extended by the class or super class. 
	 * @param clazz on the parameter go through all extended interfaces.
	 * @param superInterface Determines if the class or interface represented by this param {@code superInterface}
	 * is either a superclass or superinterface of, the class or interface represented by the specified interfaces extended by the param {@code clazz}
	 * @return empty returned , if no any extended interfaces 
	 */
	public static List<Class<?>> getAllInterfaces(Class<?> clazz,Class<?> superInterface){
		List<Class<?>> classes=new ArrayList<Class<?>>();
		Class<?> superClass=clazz;
		while(superClass!=null&&superClass!=Object.class){
			Class<?>[] clazzes= superClass.getInterfaces();
			if (clazzes.length>0) {
				for(int i=0;i<clazzes.length;i++){
					Class<?> interfaceClass=clazzes[i];
					getAllInterfaces(interfaceClass, superInterface, classes); 
				}
			}
			superClass=superClass.getSuperclass();
		}
		return classes;
	}
	
	/**
	 * call by {@link #getAllInterfaces(Object, Class)}
	 * @param clazz  interface . note that not class.
	 * @param superInterface
	 * @param classes
	 */
	private static void getAllInterfaces(Class<?> clazz,Class<?> superInterface,List<Class<?>> classes){
		if(clazz!=null){
			if(superInterface!=null){
				if(superInterface.isAssignableFrom(clazz)&&superInterface!=clazz){
					classes.add(clazz);
				}
			}
			else{
				classes.add(clazz);
			}
			Class<?>[] clazzes= clazz.getInterfaces();
			for(int i=0;i<clazzes.length;i++){
				Class<?> cls=clazzes[i];
				getAllInterfaces(cls, superInterface, classes);
			}
		}
	}
	
	/**
	 * check whether the argument is class type of not. 
	 * @param obj
	 * @return
	 */
	public static boolean isClass(Object obj){
		return Class.class.isInstance(obj);
	}
	
}
