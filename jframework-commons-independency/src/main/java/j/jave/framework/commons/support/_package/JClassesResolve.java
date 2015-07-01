package j.jave.framework.commons.support._package;

import j.jave.framework.commons.reflect.JClassUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class JClassesResolve {

	private static JClassesResolve object=new JClassesResolve();
	
	private JClassesResolve() {
	}
	
	public static JClassesResolve get(){
		return object;
	}
	
	/**
	 * get implementation. 
	 * @param packageScan
	 * @param sup
	 * @return
	 */
	public Set<Class<?>> getImplements(JClassesScan packageScan,Class<?> sup){
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
	public Set<Class<?>> getSubClass(JClassesScan packageScan,Class<?> sup){
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
