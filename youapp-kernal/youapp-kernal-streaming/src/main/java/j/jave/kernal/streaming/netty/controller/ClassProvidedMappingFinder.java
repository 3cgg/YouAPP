package j.jave.kernal.streaming.netty.controller;

import java.lang.reflect.Method;
import java.util.List;

import j.jave.kernal.jave.support.JProvider;
import j.jave.kernal.jave.support.JResourceFinder;
import j.jave.kernal.jave.support._package.JAbstractMethodFinder;
import j.jave.kernal.jave.support._package.JDefaultMethodFilter;
import j.jave.kernal.jave.support._package.JMethodInfoProvider;
import j.jave.kernal.jave.support._package.JMethodOnSingleClassFinder;

/**
 * detect specified resources , wrap the information via {@link MappingMeta}
 * <p>its thread safely
 * @see {@link JMethodInfoProvider}
 * @author J
 */
public class ClassProvidedMappingFinder implements JProvider, JResourceFinder<ClassProvidedMappingFinder> {
	
	private JAbstractMethodFinder<MappingMeta> methodFinder;
	
	private Class<?> thisClass;
	
	public ClassProvidedMappingFinder(Class<?> clazz){
		this.thisClass=clazz;
	}

	private void clean(){
		MappingMetaInfoGen mappingMetaInfoGen=new MappingMetaInfoGen(thisClass.getClassLoader());
		methodFinder=new JMethodOnSingleClassFinder<MappingMeta>(thisClass);
		methodFinder.setMethodFilter(new JDefaultMethodFilter(){
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
		});
		methodFinder.setMethodInfo(mappingMetaInfoGen);
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
