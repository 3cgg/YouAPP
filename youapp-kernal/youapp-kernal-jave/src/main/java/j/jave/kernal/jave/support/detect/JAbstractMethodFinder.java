/**
 * 
 */
package j.jave.kernal.jave.support.detect;

import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support._package.JClassesScanConfig;
import j.jave.kernal.jave.support._package.JClassesScanDefaultConfiguration;
import j.jave.kernal.jave.support._package.JClassesScanner;
import j.jave.kernal.jave.support._package.JDefaultClassesScanner;
import j.jave.kernal.jave.support.detect.JMethodInfoProvider.JMethodInfoGen;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * default scan all classes in the CLASSPATH to detect the method . 
 *<p> you also can define customized implementation of {@code JPackageScan}  to provide whose classes need be processed.
 * @see JDefaultClassesScanner
 * @see JClassesScanner
 * @author J
 * @param <T>  the same as generic of {@link JMethodInfoGen }
 */
public abstract class JAbstractMethodFinder<T> extends JClassesScanDefaultConfiguration 
	implements JClassesScanConfig ,JResourceFinder<JMethodInfoProvider<T>> {

	public static interface JMethodFilter{
		
		/**
		 * do with the method or not,  then method can be done next if false.
		 * skip the method if true.
		 * @param method method signature information.
		 * @param classIncudeMethod class information which includes the method above
		 * @return
		 */
		boolean filter(Method method,Class<?> classIncudeMethod);
		
		/**
		 * do with the class or not, then class can be done next if false.
		 * skip the class if true.
		 * @param clazz
		 * @return
		 */
		boolean filter(Class<?> clazz);
		
		/**
		 * scan all methods whose modify is in the scope of , then do filter using {@code filter(Method method,Class<?> classIncudeMethod)}
		 * @return
		 */
		int[] methodModifiers();
		
	}
	
	private static final JMethodFilter defaultMethodFilter=new JDefaultMethodFilter();
	
	private static final JDefaultMethodMetaGen defaultMethodInfo=new JDefaultMethodMetaGen();
	
	private JMethodFilter methodFilter=defaultMethodFilter;
	
	private JMethodInfoGen<T> methodInfoGen= (JMethodInfoGen<T>) defaultMethodInfo;
	
	/**
	 * @param methodFilter the methodFilter to set
	 */
	public void setMethodFilter(JMethodFilter methodFilter) {
		this.methodFilter = methodFilter;
	}
	
	/**
	 * @param methodInfoGen the methodInfoGen to set
	 */
	public void setMethodInfo(JMethodInfoGen<T> methodInfoGen) {
		this.methodInfoGen = methodInfoGen;
	}
	
	/**
	 * ANY expected value from {@link JMethodInfoGen}
	 */
	private List<T> methodInfos=new ArrayList<T>();
	
	/**
	 * KEY : class type . 
	 * VALUE : ANY expected value from {@link JMethodInfoGen}.
	 */
	private Map<Class<?>, List<T>> classMethodInfos=new ConcurrentHashMap<Class<?>, List<T>>();

	private void processClasses(Set<Class<?>> classes) {
		if(classes!=null){ 
			for (Iterator<Class<?>>  iterator = classes.iterator(); iterator.hasNext();) {
				Class<?> clazz = iterator.next();
				if(!methodFilter.filter(clazz)){
					List<Method> methods=JClassUtils.getMethods(clazz, true, methodFilter.methodModifiers());
					for(int i=0;i<methods.size();i++){
						Method method=methods.get(i);
						if(!methodFilter.filter(method, clazz)){
							T obj= methodInfoGen.getInfo(method, clazz);
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
	
	protected void doClean(){
		
	}

	public final JMethodInfoProvider<T> refresh(){
		clean();
		return find();
	}
	
	private void clean(){
		methodInfos=new ArrayList<T>();
		classMethodInfos=new ConcurrentHashMap<Class<?>, List<T>>();
		doClean();
	}
	
	protected abstract JClassesScanner classesScanner();
	
	/**
	 * @param clazz
	 */
	private void doFind(){
		Set<Class<?>> classes=classesScanner().scan();
		// filter on the classes 
		processClasses(classes);
	}
	
	@Override
	public JMethodInfoProvider<T> find() {
		doFind();
		return wrap();
	}

	private JMethodInfoProvider<T> wrap() {
		return new JMethodInfoProvider<T>(){
			@Override
			public List<T> getMethodInfos() {
				return JAbstractMethodFinder.this.methodInfos;
			}

			@Override
			public Map<Class<?>, List<T>> getClassMethodInfos() {
				return JAbstractMethodFinder.this.classMethodInfos;
			}
			
		};
	}
	
	@Override
	public JMethodInfoProvider<T> cache() {
		return wrap();
	}
	
}
