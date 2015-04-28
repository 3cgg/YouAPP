/**
 * 
 */
package j.jave.framework.support.detect;

import j.jave.framework.reflect.JClassUtils;
import j.jave.framework.support._package.JClassProvidedScan;
import j.jave.framework.support._package.JDefaultPackageScan;
import j.jave.framework.support._package.JPackageScan;
import j.jave.framework.support._package.JPackageScanConfigure;
import j.jave.framework.support._package.JPackageScanDefaultConfiguration;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * default scan all classes in the CLASSPATH to detect the method . 
 *<p> you also can define customized implementation of {@code JPackageScan}  to provide whose classes need be processed.
 * @see JDefaultPackageScan
 * @see JPackageScan
 * @author J
 * @param <T>  the same as generic of {@link JMethodInfo }
 */
public class JMethodDetect<T> extends JPackageScanDefaultConfiguration 
	implements JPackageScanConfigure ,JMethodInfoProvider<T> {

	public static interface JMethodFilter{
		
		/**
		 * do with the method or not, 
		 * @param method method signature information.
		 * @param classIncudeMethod class information which includes the method above
		 * @return
		 */
		boolean filter(Method method,Class<?> classIncudeMethod);
		
		/**
		 * do with the class or not, 
		 * @param clazz
		 * @return
		 */
		boolean filter(Class<?> clazz);
		
	}
	
	private static final JMethodFilter defaultMethodFilter=new JMethodFilter() {
		@Override
		public boolean filter(Method method, Class<?> classIncudeMethod) {
			return false;
		}
		
		public boolean filter(java.lang.Class<?> clazz) {
			return false;
		};
	};
	
	private JMethodInfo<T> methodInfo;
	
//	private static final JMethodInfo<String> defaultMethodInfo=new JMethodInfo<String>() {
//		@Override
//		public String getInfo(Method method, Class<?> classIncudeMethod) {
//			return method.getName();
//		}
//	};
	
	private JMethodFilter methodFilter=null;
	
	public JMethodDetect(JMethodInfo<T> methodInfo) {
		methodFilter=defaultMethodFilter;
		this.methodInfo=methodInfo;
	} 
	
	/**
	 * 
	 * @param methodFilter implementation of {@link JMethodFilter}
	 * @param methodInfo implementation of {@link JMethodInfo}
	 */
	public JMethodDetect(JMethodFilter methodFilter,JMethodInfo<T> methodInfo) {
		this.methodFilter=methodFilter;
		this.methodInfo=methodInfo;
	} 
	
	/**
	 * @param methodFilter the methodFilter to set
	 */
	public void setMethodFilter(JMethodFilter methodFilter) {
		this.methodFilter = methodFilter;
	}
	
	/**
	 * @param methodInfo the methodInfo to set
	 */
	public void setMethodInfo(JMethodInfo<T> methodInfo) {
		this.methodInfo = methodInfo;
	}
	
	
	private JPackageScan packageScan=null;
	
	/**
	 * @param packageScan the packageScan to set
	 */
	public void setPackageScan(JPackageScan packageScan) {
		this.packageScan = packageScan;
	}
	
	/**
	 * ANY expected value from {@link JMethodInfo}
	 */
	private List<T> methodInfos=new ArrayList<T>();
	
	/**
	 * KEY : class type . 
	 * VALUE : ANY expected value from {@link JMethodInfo}.
	 */
	private Map<Class<?>, List<T>> classMethodInfos=new ConcurrentHashMap<Class<?>, List<T>>();
	
	/**
	 * @return the methodInfos
	 */
	public List<T> getMethodInfos() {
		return  methodInfos;
	}
	
	/**
	 * @return the classMethodInfos
	 */
	public Map<Class<?>, List<T>> getClassMethodInfos() {
		return classMethodInfos;
	}
	
	/**
	 * detect classes via {@code packageScan} field, which set previously.
	 * @param superClass
	 */
	public void detect(){
		Set<Class<?>> classes=packageScan.scan();
		// filter on the classes 
		processClasses(classes);
	}
	
	/**
	 * detect classes via {@link JClassProvidedScan }.
	 * @param clazz
	 */
	public void detect(Class<?>... clazz){
		JClassProvidedScan classProvidedScan = new JClassProvidedScan(clazz);
		Set<Class<?>> classes=classProvidedScan.scan();
		// filter on the classes 
		processClasses(classes);
	}
	
	/**
	 * detect classes via {@link JDefaultPackageScan }.
	 * @param superClass
	 * @param scanOnClasspath  only true accepted.
	 */
	public void detect(Class<?> superClass,boolean scanOnClasspath){
		if(superClass==null)
			throw new IllegalArgumentException("null param not supported.");
		if(!scanOnClasspath){
			throw new IllegalArgumentException("only true supported.");
		}
		// initialize all 
		JDefaultPackageScan defaultPackageScan = new JDefaultPackageScan(
				superClass);
		defaultPackageScan.setIncludePackages(includePackages);
		defaultPackageScan.setExpression(expression);
		defaultPackageScan.setIncludeClassNames(includeClassNames);
		defaultPackageScan.setClassLoader(classLoader);
		Set<Class<?>> classes=defaultPackageScan.scan();
		
		// filter on the classes 
		processClasses(classes);
	}

	private void processClasses(Set<Class<?>> classes) {
		if(classes!=null){ 
			for (Iterator<Class<?>>  iterator = classes.iterator(); iterator.hasNext();) {
				Class<?> clazz = iterator.next();
				if(!methodFilter.filter(clazz)){
					List<Method> methods=JClassUtils.getMethods(clazz, true, Modifier.PUBLIC);
					for(int i=0;i<methods.size();i++){
						Method method=methods.get(i);
						if(!methodFilter.filter(method, clazz)){
							T obj= methodInfo.getInfo(method, clazz);
							methodInfos.add(obj);
							
							putClassObjects(clazz, obj);
						}
					}
				}
			}
		}
	}

	private void putClassObjects(Class<?> clazz, T obj) {
		List<T> infos=classMethodInfos.get(clazz);
		if(infos!=null){
			infos.add(obj);
		}
		else{
			infos=new ArrayList<T>();
			infos.add(obj);
			classMethodInfos.put(clazz, infos);
		}
	}
	
}
