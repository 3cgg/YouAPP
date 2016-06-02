package j.jave.platform.standalone.server.controller;

import j.jave.kernal.jave.support.detect.JMethodDetector;
import j.jave.kernal.jave.support.detect.JMethodDetector.JMethodFilter;
import j.jave.kernal.jave.support.detect.JMethodInfoProvider;
import j.jave.kernal.jave.support.detect.JProvider;
import j.jave.kernal.jave.support.detect.JResourceDetector;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * detect specified resources , wrap the information via {@link MappingMeta}
 * <p>its thread safely
 * @see {@link JMethodInfoProvider}
 * @author J
 */
public class ClassProvidedMappingDetector implements JProvider, JResourceDetector<ClassProvidedMappingDetector> {
	private JMethodDetector<MappingMeta> methodDetector;
	
	private Class<?> thisClass;
	
	public ClassProvidedMappingDetector(Class<?> clazz){
		this.thisClass=clazz;
		initMethodDetector();
	}
	
	static final JMethodFilter methodFilter=new JMethodFilter() {
		@Override
		public boolean filter(Class<?> clazz) {
			return !ControllerService.class.isAssignableFrom(clazz)
					||Modifier.isInterface(clazz.getModifiers())
					||Modifier.isAbstract(clazz.getModifiers())
					||Modifier.isProtected(clazz.getModifiers())
					||Modifier.isPrivate(clazz.getModifiers())
					;
		}
		
		@Override
		public boolean filter(Method method, Class<?> classIncudeMethod) {
			boolean isFilter=false;
			
			// if represent by Controller.
			isFilter=!ControllerService.class.isAssignableFrom(classIncudeMethod);
			
			if(!isFilter){
				// if static method.
				isFilter=Modifier.isStatic(method.getModifiers())
						||Modifier.isNative(method.getModifiers())
						;
			}
			isFilter=method.getAnnotation(JRequestMapping.class)==null;
			return isFilter;
		}
		
		@Override
		public int[] methodModifiers() {
			return new int[]{Modifier.PUBLIC};
		}
	};

	private void initMethodDetector(){
		MappingMetaInfoGen mappingMetaInfoGen=new MappingMetaInfoGen(thisClass.getClassLoader());
		methodDetector=new JMethodDetector<MappingMeta>(methodFilter,
				mappingMetaInfoGen);
	}
	
//	private volatile boolean flag=true;
//	private final Object lock=new Object();
	
	/**
	 * get all resource informations fist time, 
	 * the same one returned if call subsequently.
	 * @return
	 */
	public synchronized ClassProvidedMappingDetector detect(){
//		if(flag){
//			synchronized (lock) {
//				if(flag){
//					methodDetector.detect(superClass, true);
//					flag=false;
//				}
//			}
//		}
		methodDetector.detect(thisClass);
		return this;
	}
	
	
	/**
	 * force refresh the resources. scan and wrap resources every time , 
	 * a new {@link JMethodInfoProvider} returned every time. 
	 */
	public synchronized ClassProvidedMappingDetector refresh(){
		initMethodDetector();
		detect();
		return this;
	}
	
	public List<MappingMeta> getMappingMetas(){
		return methodDetector.getMethodInfos();
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
