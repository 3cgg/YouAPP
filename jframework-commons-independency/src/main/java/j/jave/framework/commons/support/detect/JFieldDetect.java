/**
 * 
 */
package j.jave.framework.commons.support.detect;

import j.jave.framework.commons.reflect.JClassUtils;
import j.jave.framework.commons.support._package.JClassProvidedScanner;
import j.jave.framework.commons.support._package.JClassesScan;
import j.jave.framework.commons.support._package.JClassesScanConfig;
import j.jave.framework.commons.support._package.JClassesScanDefaultConfiguration;
import j.jave.framework.commons.support._package.JDefaultClassesScanner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * default scan all classes in the CLASSPATH to detect the field . 
 *<p> you also can define customized implementation of {@code JClassesScan}  to provide whose classes need be processed.
 * @see JDefaultClassesScanner
 * @see JClassesScan
 * @author J
 * @param <T>  the same as generic of {@link JFieldInfo }
 */
public class JFieldDetect<T> extends JClassesScanDefaultConfiguration 
	implements JClassesScanConfig ,JFieldInfoProvider<T> {

	public static interface JFieldFilter{
		
		/**
		 * do with the field or not,  then method can be done next if false.
		 * skip the method if true.
		 * @param field field signature information.
		 * @param classIncudeField class information which includes the field above
		 * @return
		 */
		boolean filter(Field field,Class<?> classIncudeField);
		
		/**
		 * do with the class or not, then class can be done next if false.
		 * skip the class if true.
		 * @param clazz
		 * @return
		 */
		boolean filter(Class<?> clazz);
		
		/**
		 * scan all methods whose modify is in the scope of , then do filter using {@code filter(Field field,Class<?> classIncudeField)}
		 * @return
		 */
		int[] fieldModifiers();
		
	}
	
	private static final JFieldFilter defaultFieldFilter=new JFieldFilter() {

		@Override
		public boolean filter(Field field, Class<?> classIncudeField) {
			return false;
		}

		@Override
		public boolean filter(Class<?> clazz) {
			return false;
		}

		@Override
		public int[] fieldModifiers() {
			return new int[]{Modifier.PUBLIC,Modifier.PROTECTED,Modifier.PRIVATE};
		}
		
	};
	
	private JFieldInfo<T> fieldInfo;
	
//	private static final JMethodInfo<String> defaultMethodInfo=new JMethodInfo<String>() {
//		@Override
//		public String getInfo(Method method, Class<?> classIncudeMethod) {
//			return method.getName();
//		}
//	};
	
	private JFieldFilter fieldFilter=null;
	
	public JFieldDetect(JFieldInfo<T> fieldInfo) {
		fieldFilter=defaultFieldFilter;
		this.fieldInfo=fieldInfo;
	} 
	
	/**
	 * 
	 * @param fieldFilter implementation of {@link JFieldFilter}
	 * @param fieldInfo implementation of {@link JFieldInfo}
	 */
	public JFieldDetect(JFieldFilter fieldFilter,JFieldInfo<T> fieldInfo) {
		this.fieldFilter=fieldFilter;
		this.fieldInfo=fieldInfo;
	} 
	
	public void setFieldFilter(JFieldFilter fieldFilter) {
		this.fieldFilter = fieldFilter;
	}
	
	public void setFieldInfo(JFieldInfo<T> fieldInfo) {
		this.fieldInfo = fieldInfo;
	}
	
	
	private JClassesScan classesScan=null;
	
	public void setClassesScan(JClassesScan classesScan) {
		this.classesScan = classesScan;
	}
	
	/**
	 * ANY expected value from {@link JFieldInfo}
	 */
	private List<T> fieldInfos=new ArrayList<T>();
	
	/**
	 * KEY : class type . 
	 * VALUE : ANY expected value from {@link JFieldInfo}.
	 */
	private Map<Class<?>, List<T>> classFieldInfos=new ConcurrentHashMap<Class<?>, List<T>>();
	
	public List<T> getFieldInfos() {
		return fieldInfos;
	}
	
	@Override
	public Map<Class<?>, List<T>> getClassFieldInfos() {
		return classFieldInfos;
	}
	
	/**
	 * detect classes via {@code packageScan} field, which set previously.
	 * @param superClass
	 */
	public void detect(){
		Set<Class<?>> classes=classesScan.scan();
		// filter on the classes 
		processClasses(classes);
	}
	
	/**
	 * detect classes via {@link JClassProvidedScanner }.
	 * @param clazz
	 */
	public void detect(Class<?>... clazz){
		JClassProvidedScanner classProvidedScan = new JClassProvidedScanner(clazz);
		Set<Class<?>> classes=classProvidedScan.scan();
		// filter on the classes 
		processClasses(classes);
	}
	
	/**
	 * detect classes via {@link JDefaultClassesScanner }.
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
		JDefaultClassesScanner defaultPackageScan = new JDefaultClassesScanner(
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
				if(!fieldFilter.filter(clazz)){
					List<Field> fields=JClassUtils.getFields(clazz, true, fieldFilter.fieldModifiers());
					for(int i=0;i<fields.size();i++){
						Field field=fields.get(i);
						if(!fieldFilter.filter(field, clazz)){
							T obj= fieldInfo.getInfo(field, clazz);
							fieldInfos.add(obj);
							
							putClassObjects(clazz, obj);
						}
					}
				}
			}
		}
	}

	private void putClassObjects(Class<?> clazz, T obj) {
		List<T> infos=classFieldInfos.get(clazz);
		if(infos!=null){
			infos.add(obj);
		}
		else{
			infos=new ArrayList<T>();
			infos.add(obj);
			classFieldInfos.put(clazz, infos);
		}
	}
	
}
