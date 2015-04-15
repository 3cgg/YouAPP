package j.jave.framework._package;

import j.jave.framework.reflect.JReflect;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class JPackageResolve {

	private static JPackageResolve object=new JPackageResolve();
	
	private JPackageResolve() {
	}
	
	public static JPackageResolve get(){
		return object;
	}
	
	/**
	 * get implementation. 
	 * @param packageScan
	 * @param sup
	 * @return
	 */
	public Set<Class<?>> getImplements(JPackageScan packageScan,Class<?> sup){
		Set<Class<?>> clazzes=packageScan.scan();
		Set<Class<?>> classes=new HashSet<Class<?>>();
		if(clazzes!=null){
			for (Iterator<Class<?>> iterator = clazzes.iterator(); iterator.hasNext();) {
				Class<?> clazz = iterator.next();
				if(sup.isAssignableFrom(clazz)&&sup!=clazz){
					if(JReflect.isNewInstanceable(clazz)){  // filter "interface , abstract "
						classes.add(clazz);
					}
				}
			}
		}
		return classes;
	}
	
	
	
	
	
	
	
}
