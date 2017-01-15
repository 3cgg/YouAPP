/**
 * 
 */
package me.bunny.kernel._c.reflect;

import static java.util.Locale.ENGLISH;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.bunny.kernel._c.utils.JAssert;
import me.bunny.kernel._c.utils.JDateUtils;
import me.bunny.kernel._c.utils.JNumberUtils;
import me.bunny.kernel._c.utils.JStringUtils;

/**
 * utils collection for relfecting class. 
 * @author J
 */
public abstract class JClassUtils {

	/** Suffix for array class names: "[]" */
	public static final String ARRAY_SUFFIX = "[]";

	/** Prefix for internal array class names: "[" */
	private static final String INTERNAL_ARRAY_PREFIX = "[";

	/** Prefix for internal non-primitive array class names: "[L" */
	private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";

	/** The package separator character '.' */
	private static final char PACKAGE_SEPARATOR = '.';

	/** The path separator character '/' */
	private static final char PATH_SEPARATOR = '/';

	/** The inner class separator character '$' */
	private static final char INNER_CLASS_SEPARATOR = '$';

	/** The CGLIB class separator character "$$" */
	public static final String CGLIB_CLASS_SEPARATOR = "$$";

	/** The ".class" file suffix */
	public static final String CLASS_FILE_SUFFIX = ".class";
	
	/**
	 * get value from object via calling getter method. 
	 * @param property
	 * @param model
	 * @return
	 * @see JPropertyNotFoundException
	 * @see JClassException
	 */
	public static Object get(String property, Object model) {
		Method method=findGetterMethod(model.getClass(), property);
		try {
			return method.invoke(model);
		} catch (Exception e) {
			throw new JClassException(e);
		}
	}
	
	/**
	 * get value via property name, directly return the property value,not call the getter method.
	 * @param property
	 * @param model
	 * @param if scan super class, or not
	 * @return
	 */
	public static Object getByField(String property, Object model,boolean deap) {
		try {
			Class<?> clazz = model.getClass();
			Field field=getField(property, clazz,deap);
			JAssert.isNotNull(field, "cannot find field in the class :  "+clazz);
			field.setAccessible(true);
			return field.get(model);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param containerClass
	 * @param propertyName
	 * @return
	 * @see JPropertyNotFoundException
	 */
	public static Method findGetterMethod(Class<?> containerClass, String propertyName) {
		Class<?> checkClass = containerClass;
		Method getter = null;

		// check containerClass, and then its super types (if any)
		while ( getter == null && checkClass != null ) {
			if ( checkClass.equals( Object.class ) ) {
				break;
			}

			getter = getGetterOrNull( checkClass, propertyName );
			checkClass = checkClass.getSuperclass();
		}

		// if no getter found yet, check all implemented interfaces
		if ( getter == null ) {
			for ( Class<?> theInterface : containerClass.getInterfaces() ) {
				getter = getGetterOrNull( theInterface, propertyName );
				if ( getter != null ) {
					break;
				}
			}
		}

		if ( getter == null ) {
			throw new JPropertyNotFoundException(
					String.format(
							Locale.ROOT,
							"Could not locate getter method for property [%s#%s]",
							containerClass.getName(),
							propertyName
					)
			);
		}

		getter.setAccessible( true );
		return getter;
	}
	
	private static Method getGetterOrNull(Class<?> containerClass, String propertyName) {
		for ( Method method : containerClass.getDeclaredMethods() ) {
			// if the method has parameters, skip it
			if ( method.getParameterTypes().length != 0 ) {
				continue;
			}

			// if the method is a "bridge", skip it
			if ( method.isBridge() ) {
				continue;
			}

			final String methodName = method.getName();

			// try "get"
			if ( methodName.startsWith( "get" ) ) {
				final String stemName = methodName.substring( 3 );
				final String decapitalizedStemName = Introspector.decapitalize( stemName );
				if ( stemName.equals( propertyName ) || decapitalizedStemName.equals( propertyName ) ) {
					verifyNoIsVariantExists( containerClass, propertyName, method, stemName );
					return method;
				}

			}

			// if not "get", then try "is"
			if ( methodName.startsWith( "is" ) ) {
				final String stemName = methodName.substring( 2 );
				String decapitalizedStemName = Introspector.decapitalize( stemName );
				if ( stemName.equals( propertyName ) || decapitalizedStemName.equals( propertyName ) ) {
					verifyNoGetVariantExists( containerClass, propertyName, method, stemName );
					return method;
				}
			}
		}

		return null;
	}
	
	private static void verifyNoGetVariantExists(
			Class<?> containerClass,
			String propertyName,
			Method isMethod,
			String stemName) {
		// verify that the Class does not also define a method with the same stem name with 'is'
		try {
			final Method getMethod = containerClass.getDeclaredMethod( "get" + stemName );
			// No such method should throw the caught exception.  So if we get here, there was
			// such a method.
			checkGetAndIsVariants( containerClass, propertyName, getMethod, isMethod );
		}
		catch (NoSuchMethodException ignore) {
		}
	}
	
	private static void verifyNoIsVariantExists(
			Class<?> containerClass,
			String propertyName,
			Method getMethod,
			String stemName) {
		// verify that the Class does not also define a method with the same stem name with 'is'
		try {
			final Method isMethod = containerClass.getDeclaredMethod( "is" + stemName );
			// No such method should throw the caught exception.  So if we get here, there was
			// such a method.
			checkGetAndIsVariants( containerClass, propertyName, getMethod, isMethod );
		}
		catch (NoSuchMethodException ignore) {
		}
	}
	
	
	private static void checkGetAndIsVariants(
			Class<?> containerClass,
			String propertyName,
			Method getMethod,
			Method isMethod) {
		// Check the return types.  If they are the same, its ok.  If they are different
		// we are in a situation where we could not reasonably know which to use.
		if ( !isMethod.getReturnType().equals( getMethod.getReturnType() ) ) {
			throw new JPropertyNotFoundException(
					String.format(
							Locale.ROOT,
							"In trying to locate getter for property [%s], Class [%s] defined " +
									"both a `get` [%s] and `is` [%s] variant",
							propertyName,
							containerClass.getName(),
							getMethod.toString(),
							isMethod.toString()
					)
			);
		}
	}
	
	/**
	 * set property value via calling setter method.
	 * @param property
	 * @param value
	 * @param model
	 * @return
	 * @see JPropertyNotFoundException
	 * @see JClassException
	 */
	public static void set(String property,Object value,Object model) {
		Method method=findSetterMethod(model.getClass(),property, getType(model, property));
		try {
			method.invoke(model, value);
		} catch (Exception e) {
			throw new JClassException(e);
		}
	}
	
	/**
	 * 
	 * @param containerClass
	 * @param propertyName
	 * @param propertyType
	 * @return
	 * @see JPropertyNotFoundException
	 */
	public static Method findSetterMethod(Class<?> containerClass, String propertyName, 
			Class<?> propertyType) {
		Class<?> checkClass = containerClass;
		Method setter = null;

		// check containerClass, and then its super types (if any)
		while ( setter == null && checkClass != null ) {
			if ( checkClass.equals( Object.class ) ) {
				break;
			}

			setter = setterOrNull( checkClass, propertyName, propertyType );
			checkClass = checkClass.getSuperclass();
		}

		// if no setter found yet, check all implemented interfaces
		if ( setter == null ) {
			for ( Class<?> theInterface : containerClass.getInterfaces() ) {
				setter = setterOrNull( theInterface, propertyName, propertyType );
				if ( setter != null ) {
					break;
				}
			}
		}

		if ( setter == null ) {
			throw new JPropertyNotFoundException(
					String.format(
							Locale.ROOT,
							"Could not locate setter method for property [%s#%s]",
							containerClass.getName(),
							propertyName
					)
			);
		}

		setter.setAccessible( true );
		return setter;
	}

	private static Method setterOrNull(Class<?> theClass, String propertyName, 
			Class<?> propertyType) {
		Method potentialSetter = null;

		for ( Method method : theClass.getDeclaredMethods() ) {
			final String methodName = method.getName();
			if ( method.getParameterTypes().length == 1 && methodName.startsWith( "set" ) ) {
				final String testOldMethod = methodName.substring( 3 );
				final String testStdMethod = Introspector.decapitalize( testOldMethod );
				if ( testStdMethod.equals( propertyName ) || testOldMethod.equals( propertyName ) ) {
					potentialSetter = method;
					if ( propertyType == null || method.getParameterTypes()[0].equals( propertyType ) ) {
						break;
					}
				}
			}
		}

		return potentialSetter;
	}
	
	/**
	 * apply value directly via setting on the property.
	 * @param property
	 * @param value
	 * @param model
	 * @param deep
	 */
	public static void setByField(String property,Object value,Object model,boolean deep) {
		try {
			Class<?> clazz = model.getClass();
			Field field=getField(property, clazz,deep);
			JAssert.isNotNull(field, "cannot find field in the class :  "+clazz);
			field.setAccessible(true);
			field.set(model, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * set value
	 * @param field
	 * @param value
	 * @param model
	 */
	public static void setOnField(Field field,Object value,Object model) {
		try {
			field.setAccessible(true);
			field.set(model, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * get field. 
	 * @param property
	 * @param model
	 * @param deep whether scan upto super class
	 * @return
	 */
	public static Field getField(String property , Class<?> clazz,boolean deep){
		if(clazz==null) return null;
		if(JStringUtils.isNullOrEmpty(property)) return null;
		Field field=null;
		Class<?> superClass=clazz;
		while(superClass!=null){
			try {
				field=superClass.getDeclaredField(property);
				if(field!=null) break;
			} catch (NoSuchFieldException e) {
				if(deep){
					superClass=superClass.getSuperclass();
					continue;
				}
				else{
					break;
				}
			} catch (SecurityException e) {
				throw e;
			}
		}
		return field;
	}
	
	/**
	 * 
	 * @param property
	 * @param clazz
	 * @param deep whether scan upto super class
	 * @param parameterTypes
	 * @return
	 */
	public static Method getMethod(String methodName,Class<?> clazz,boolean deep,Class<?>... parameterTypes){
		if(clazz==null) return null;
		if(JStringUtils.isNullOrEmpty(methodName)) return null;
		Method method=null;
		Class<?> superClass=clazz;
		while(superClass!=null){
			try {
				method=superClass.getMethod(methodName, parameterTypes);
				if(method!=null) break;
			} catch (NoSuchMethodException e) {
				if(deep){
					superClass=superClass.getSuperclass();
					continue;
				}
				else{
					break;
				}
			} catch (SecurityException e) {
				throw e;
			}
		}
		return method;
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
		
		if(modifiers!=null&&modifiers.length==1&&modifiers[0]==Modifier.PUBLIC){
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
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> load(String className,ClassLoader classLoader){
		try {
			if(classLoader==null){
				classLoader=Thread.currentThread().getContextClassLoader();
			}
			return (Class<T>) classLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new JClassException(e) ;
		}
	}
	
	/**
	 * 
	 * @param className
	 * @return
	 *  @throws JClassException
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> load(String className){
		try {
			ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
			return (Class<T>) classLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new JClassException(e) ;
		}
	}
	
	/**
	 * see {@link Class#isAssignableFrom(Class)}
	 * @param superClass
	 * @param testClass
	 * @param excludeSelf whether excluding the super class self
	 * @return
	 */
	public static boolean isAssignable(Class<?> superClass,Class<?> testClass,boolean excludeSelf){
		boolean isAssginable=superClass.isAssignableFrom(testClass);
		if(isAssginable&&excludeSelf){
			isAssginable=superClass!=testClass;
		}
		return isAssginable;
	}
	
	/**
	 * the method is the same as {@link #isAssignable(Class, Class, boolean)} , only passing the false argument
	 * @param superClass
	 * @param testClass
	 * @return
	 */
	public static boolean isAssignable(Class<?> superClass,Class<?> testClass){
		return isAssignable(superClass, testClass, false);
	}
	
	
	/**
	 * get all fields , includes those fields in the {@link Object}
	 * @param clazz
	 * @param deep
	 * @param modifiers  , to filter the method modify is between 
	 * @return
	 */
	public static List<Field> getFields(Class<?> clazz,boolean deep,int... modifiers){
		List<Field> fields=new ArrayList<Field>();
		Class<?> superClass=clazz;
		
		if(modifiers!=null&&modifiers.length==1&&modifiers[0]==Modifier.PUBLIC){
			if(deep){
				Collections.addAll(fields, superClass.getFields());
				return fields;
			}
		}
		
		while(superClass!=null){
			Field[] feds=superClass.getDeclaredFields();
			for(int i=0;i<feds.length;i++){
				Field field=feds[i];
				if(!isAccessable(field)){
					field.setAccessible(true);
				}
				boolean exists=modifiers.length==0;
				if(modifiers!=null&&modifiers.length>0){
					for(int mdf=0;mdf<modifiers.length;mdf++){
						if(field.getModifiers()==modifiers[mdf]){
							exists=true;
							break;
						}
					}
				}
				
				if(exists){
					fields.add(field);
				}
			}
			
			if(deep){ // deep scanning
				superClass=superClass.getSuperclass();
			}
			else{
				superClass=null; // break;
			}
		}
		return fields;
	}
	
	public static Object newObject(Class<?> clazz){
		try{
			return clazz.newInstance();
		}catch(Exception e){
			throw new JClassException(e);
		}
	}
	
	public static boolean isSimpleType(Class<?> clazz){
		return String.class.isAssignableFrom(clazz)
				||Integer.class.isAssignableFrom(clazz)
				||int.class.isAssignableFrom(clazz)
				||Long.class.isAssignableFrom(clazz)
				||long.class.isAssignableFrom(clazz)
				||Double.class.isAssignableFrom(clazz)
				||double.class.isAssignableFrom(clazz)
				||Float.class.isAssignableFrom(clazz)
				||float.class.isAssignableFrom(clazz)
				||Byte.class.isAssignableFrom(clazz)
				||byte.class.isAssignableFrom(clazz)
				||BigDecimal.class.isAssignableFrom(clazz)
				||boolean.class.isAssignableFrom(clazz)
				||Boolean.class.isAssignableFrom(clazz)
				||Date.class.isAssignableFrom(clazz)
				||Timestamp.class.isAssignableFrom(clazz);
	}
	
	public static boolean isSimpleTypeArray(Class<?> clazz){
		if(!clazz.isArray()) return false;
		return String[].class.isAssignableFrom(clazz)
				||Integer[].class.isAssignableFrom(clazz)
				||int[].class.isAssignableFrom(clazz)
				||Long[].class.isAssignableFrom(clazz)
				||long[].class.isAssignableFrom(clazz)
				||Double[].class.isAssignableFrom(clazz)
				||double[].class.isAssignableFrom(clazz)
				||Float[].class.isAssignableFrom(clazz)
				||float[].class.isAssignableFrom(clazz)
				||Byte[].class.isAssignableFrom(clazz)
				||byte[].class.isAssignableFrom(clazz)
				||BigDecimal[].class.isAssignableFrom(clazz)
				||boolean[].class.isAssignableFrom(clazz)
				||Boolean[].class.isAssignableFrom(clazz)
				||Date[].class.isAssignableFrom(clazz)
				||Timestamp[].class.isAssignableFrom(clazz);
	}
	
	/**
	 * Return the default ClassLoader to use: typically the thread context
	 * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
	 * class will be used as fallback.
	 * <p>Call this method if you intend to use the thread context ClassLoader
	 * in a scenario where you clearly prefer a non-null ClassLoader reference:
	 * for example, for class path resource loading (but not necessarily for
	 * {@code Class.forName}, which accepts a {@code null} ClassLoader
	 * reference as well).
	 * @return the default ClassLoader (only {@code null} if even the system
	 * ClassLoader isn't accessible)
	 * @see Thread#getContextClassLoader()
	 * @see ClassLoader#getSystemClassLoader()
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}
		catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = JClassUtils.class.getClassLoader();
			if (cl == null) {
				// getClassLoader() returning null indicates the bootstrap ClassLoader
				try {
					cl = ClassLoader.getSystemClassLoader();
				}
				catch (Throwable ex) {
					// Cannot access system ClassLoader - oh well, maybe the caller can live with null...
				}
			}
		}
		return cl;
	}
	
	/**
	 * get parameter name
	 * @param method
	 * @return
	 */
	public static String[] getMethodNames(Method method){
		Parameter[] parameters= method.getParameters();
		String[] paramNames=new String[parameters.length];
		int boundIndex=0;
		for(Parameter parameter:parameters){
			paramNames[boundIndex]=parameter.getName();
			boundIndex++;
		}
		return paramNames;
	}
	
	/**
	 * Check whether the given object is a CGLIB proxy.
	 * @param object the object to check
	 * @see org.springframework.aop.support.AopUtils#isCglibProxy(Object)
	 */
	public static boolean isCglibProxy(Object object) {
		return isCglibProxyClass(object.getClass());
	}

	/**
	 * Check whether the specified class is a CGLIB-generated class.
	 * @param clazz the class to check
	 */
	public static boolean isCglibProxyClass(Class<?> clazz) {
		return (clazz != null && isCglibProxyClassName(clazz.getName()));
	}

	/**
	 * Check whether the specified class name is a CGLIB-generated class.
	 * @param className the class name to check
	 */
	public static boolean isCglibProxyClassName(String className) {
		return (className != null && className.contains(CGLIB_CLASS_SEPARATOR));
	}
	
	/**
	 * Determine whether the given field is a "public static final" constant.
	 * @param field the field to check
	 */
	public static boolean isPublicStaticFinal(Field field) {
		int modifiers = field.getModifiers();
		return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
	}
	
	/**
	 * Determine whether the given method is declared by the user or at least pointing to
	 * a user-declared method.
	 * <p>Checks {@link Method#isSynthetic()} (for implementation methods) as well as the
	 * {@code GroovyObject} interface (for interface methods; on an implementation class,
	 * implementations of the {@code GroovyObject} methods will be marked as synthetic anyway).
	 * Note that, despite being synthetic, bridge methods ({@link Method#isBridge()}) are considered
	 * as user-level methods since they are eventually pointing to a user-declared generic method.
	 * @param method the method to check
	 * @return {@code true} if the method can be considered as user-declared; [@code false} otherwise
	 */
	public static boolean isUserLevelMethod(Method method) {
		JAssert.notNull(method, "Method must not be null");
		return (method.isBridge() || (!method.isSynthetic() && !isGroovyObjectMethod(method)));
	}

	private static boolean isGroovyObjectMethod(Method method) {
		return method.getDeclaringClass().getName().equals("groovy.lang.GroovyObject");
	}
	
	
	private static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }
	
	public static String getSetterMethodName(Field field){
		return getSetterMethodName(field.getName());
	}
	
	public static String getGetterMethodName(Field field){
		return getGetterMethodName(field.getName(), field.getType()==boolean.class||field.getType()==Boolean.class);
	}
	
	public static String getSetterMethodName(String property){
		String name=capitalize(property);
		return "set"+name;
	}
	
	
	public static String getGetterMethodName(String property,boolean isBoolean){
		String name=capitalize(property);
		return isBoolean?("is"+name):("get"+name);
		
	}
	
	
	
	
	
	
	
}
