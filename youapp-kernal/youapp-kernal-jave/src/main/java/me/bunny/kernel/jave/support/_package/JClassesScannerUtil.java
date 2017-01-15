package me.bunny.kernel.jave.support._package;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.utils.JCollectionUtils;

public class JClassesScannerUtil {
	
	/**
	 * get implementation class , exclude interface , abstract class. 
	 * @param clazzes
	 * @param superClasses
	 * @return
	 */
	public static Set<Class<?>> implementer(Set<Class<?>> clazzes,Class<?>... superClasses){
		Set<Class<?>> classes=new HashSet<Class<?>>();
		if(clazzes!=null){
			for (Iterator<Class<?>> iterator = clazzes.iterator(); iterator.hasNext();) {
				Class<?> clazz = iterator.next();
				if(JCollectionUtils.hasInArray(superClasses)){
					for(Class<?> superClass:superClasses){
						if(JClassUtils.isAssignable(superClass, clazz, true)
								&&JClassUtils.isNewInstanceable(clazz)){ // filter "interface , abstract "
							classes.add(clazz);
						}
					}
				}
			}
		}
		return classes;
	}
	
	/**
	 * get sub-class , which may be interface , abstract ...
	 * @param clazzes
	 * @param superClasses
	 * @return
	 */
	public static Set<Class<?>> subClass(Set<Class<?>> clazzes,Class<?>... superClasses){
		Set<Class<?>> classes=new HashSet<Class<?>>();
		if(clazzes!=null){
			for (Iterator<Class<?>> iterator = clazzes.iterator(); iterator.hasNext();) {
				Class<?> clazz = iterator.next();
				if(JCollectionUtils.hasInArray(superClasses)){
					for(Class<?> superClass:superClasses){
						if(JClassUtils.isAssignable(superClass, clazz, true)){
							classes.add(clazz);
						}
					}
				}
			}
		}
		return classes;
	}
	
	
	
	
	
	
	
}
