package j.jave.kernal.streaming.netty.controller;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import me.bunny.kernel.jave.support.JProvider;
import me.bunny.kernel.jave.support.JResourceFinder;
import me.bunny.kernel.jave.support._package.JAbstractMethodFinder;
import me.bunny.kernel.jave.support._package.JDefaultMethodFilter;
import me.bunny.kernel.jave.support._package.JMethodInfoProvider;
import me.bunny.kernel.jave.support._package.JMethodOnSingleClassFinder;
import me.bunny.kernel.jave.support._package.JAbstractMethodFinder.JMethodFilter;
import me.bunny.kernel.jave.support._package.JMethodInfoProvider.JMethodInfoGen;

/**
 * detect specified resources , wrap the information via {@link MappingMeta}
 * <p>its thread safely
 * @see {@link JMethodInfoProvider}
 * @author J
 */
public class ClassProvidedMappingFinder implements JProvider, JResourceFinder<ClassProvidedMappingFinder> {
	
	public static final MappingMetaFindStrategy ANNOTATION=new AnnotationFind();
	
	public static final MappingMetaFindStrategy INTERFACE=new SimpleInterfaceFind();
	
	public static final MappingMetaFindStrategy CLASS=new SimpleClassFind();
	
	
	private JAbstractMethodFinder<MappingMeta> methodFinder;
	
	private final MappingMetaFindStrategy mappingMetaFindStrategy;
	
	private final Class<?> thisClass;
	
	public ClassProvidedMappingFinder(Class<?> clazz){
		this(clazz,null);
	}
	
	public ClassProvidedMappingFinder(Class<?> clazz,MappingMetaFindStrategy mappingMetaFindStrategy){
		this.thisClass=clazz;
		MappingMetaFindStrategy defaut=mappingMetaFindStrategy==null?
				ANNOTATION:mappingMetaFindStrategy;
		this.mappingMetaFindStrategy=defaut;
	}
	
	
	private static class SimpleClassFind implements MappingMetaFindStrategy{

		@Override
		public JMethodInfoGen<MappingMeta> methodInfoGen(Class<?> clazz) {
			return new ClassMappingMetaInfoGen(clazz.getClassLoader());
		}

		@Override
		public JMethodFilter methodFilter(Class<?> clazz) {

			return new JDefaultMethodFilter(){
				@Override
				public boolean filter(Method method, Class<?> classIncudeMethod) {
					return classIncudeMethod.isInterface()
							||classIncudeMethod==Object.class;
				}
				
				@Override
				public boolean filter(Class<?> clazz) {
					return clazz.isInterface();
				}
				
				@Override
				public int[] methodModifiers() {
					return new int[]{Modifier.PUBLIC};
				}
			};
		
		}
		
	}
	
	private static class SimpleInterfaceFind implements MappingMetaFindStrategy{

		@Override
		public JMethodInfoGen<MappingMeta> methodInfoGen(Class<?> clazz) {
			return new InterfaceMappingMetaInfoGen(clazz.getClassLoader());
		}

		@Override
		public JMethodFilter methodFilter(Class<?> clazz) {

			return new JDefaultMethodFilter(){
				@Override
				public boolean filter(Method method, Class<?> classIncudeMethod) {
					return !classIncudeMethod.isInterface();
				}
				
				@Override
				public boolean filter(Class<?> clazz) {
					return !clazz.isInterface();
				}
				
				@Override
				public int[] methodModifiers() {
					return new int[]{Modifier.PUBLIC|Modifier.ABSTRACT};
				}
			};
		
		}
		
	}
	
	private static class AnnotationFind implements MappingMetaFindStrategy{

		@Override
		public JMethodInfoGen<MappingMeta> methodInfoGen(Class<?> clazz) {
			return new AnnotationMappingMetaInfoGen(clazz.getClassLoader());
		}

		@Override
		public JMethodFilter methodFilter(Class<?> clazz) {
			return new JDefaultMethodFilter(){
				@Override
				public boolean filter(Method method, Class<?> classIncudeMethod) {
					boolean valid=method.isAnnotationPresent(JRequestMapping.class);
					return !valid;
				}
				
				@Override
				public boolean filter(Class<?> clazz) {
					boolean valid=clazz.isAnnotationPresent(JRequestMapping.class);
					return !valid;
				}
				
				@Override
				public int[] methodModifiers() {
					return new int[]{Modifier.PUBLIC|Modifier.ABSTRACT};
				}
				
				
			};
		}
		
	}
	
	
	public static interface MappingMetaFindStrategy{
		
		JMethodInfoGen<MappingMeta> methodInfoGen(Class<?> clazz);
		
		JMethodFilter methodFilter(Class<?> clazz);
		
	}
	

	private void clean(){
		methodFinder=new JMethodOnSingleClassFinder<MappingMeta>(thisClass);
		methodFinder.setMethodFilter(mappingMetaFindStrategy.methodFilter(thisClass));
		methodFinder.setMethodInfo(mappingMetaFindStrategy.methodInfoGen(thisClass));
	}
	
//	private volatile boolean flag=true;
//	private final Object lock=new Object();
	
	/**
	 * get all resource informations fist time, 
	 * the same one returned if call subsequently.
	 * @return
	 */
	public synchronized ClassProvidedMappingFinder find(){
//		if(flag){
//			synchronized (lock) {
//				if(flag){
//					methodDetector.detect(superClass, true);
//					flag=false;
//				}
//			}
//		}
		clean();
		methodFinder.find();
		return this;
	}
	
	
	/**
	 * force refresh the resources. scan and wrap resources every time , 
	 * a new {@link JMethodInfoProvider} returned every time. 
	 */
	public synchronized ClassProvidedMappingFinder refresh(){
		clean();
		methodFinder.refresh();
		return this;
	}
	
	@Override
	public ClassProvidedMappingFinder cache() {
		return this;
	}
	
	public List<MappingMeta> getMappingMetas(){
		return methodFinder.cache().getMethodInfos();
	}
	
//	private static MappingDetector mappingDetector;
//	
//	public static MappingDetector getDefault(JConfiguration configuration){
//		if(mappingDetector==null){
//			synchronized (MappingDetector.class) {
//				mappingDetector=new MappingDetector(configuration);
//			}
//		}
//		return mappingDetector;
//	}
	
}
