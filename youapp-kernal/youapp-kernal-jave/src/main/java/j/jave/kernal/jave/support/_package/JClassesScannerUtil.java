package j.jave.kernal.jave.support._package;

import j.jave.kernal.jave.reflect.JClassUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class JClassesScannerUtil {
	
	/**
	 * get implementation class , exclude interface , abstract class. 
	 * @param packageScan
	 * @param sup
	 * @return
	 */
	public static Set<Class<?>> getImplements(JClassesScanner packageScan,Class<?> sup){
		Set<Class<?>> clazzes=packageScan.scan();
		Set<Class<?>> classes=new HashSet<Class<?>>();
		if(clazzes!=null){
			for (Iterator<Class<?>> iterator = clazzes.iterator(); iterator.hasNext();) {
				Class<?> clazz = iterator.next();
				if(JClassUtils.isAssignable(sup, clazz, true)){
					if(JClassUtils.isNewInstanceable(clazz)){  // filter "interface , abstract "
						classes.add(clazz);
					}
				}
			}
		}
		return classes;
	}
	
	/**
	 * get sub-class , which may be interface , abstract ...
	 * @param packageScan
	 * @param sup
	 * @return
	 */
	public static Set<Class<?>> getSubClass(JClassesScanner packageScan,Class<?> sup){
		Set<Class<?>> clazzes=packageScan.scan();
		Set<Class<?>> classes=new HashSet<Class<?>>();
		if(clazzes!=null){
			for (Iterator<Class<?>> iterator = clazzes.iterator(); iterator.hasNext();) {
				Class<?> clazz = iterator.next();
				if(JClassUtils.isAssignable(sup, clazz, true)){
					classes.add(clazz);
				}
			}
		}
		return classes;
	}
	
	
	
	
	
	
	
}
