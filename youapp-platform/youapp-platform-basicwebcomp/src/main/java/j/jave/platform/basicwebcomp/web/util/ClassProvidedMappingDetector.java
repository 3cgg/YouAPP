package j.jave.platform.basicwebcomp.web.util;

import j.jave.kernal.jave.support.detect.JMethodDetector;
import j.jave.kernal.jave.support.detect.JMethodInfoProvider;
import j.jave.kernal.jave.support.detect.JProvider;
import j.jave.kernal.jave.support.detect.JResourceDetector;

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

	private void initMethodDetector(){
		methodDetector=new JMethodDetector<MappingMeta>(MappingDetector.methodFilter,
				MappingDetector.methodInfo);
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
