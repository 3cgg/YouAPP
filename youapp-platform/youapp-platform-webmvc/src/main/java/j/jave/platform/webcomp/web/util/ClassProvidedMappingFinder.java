package j.jave.platform.webcomp.web.util;

import j.jave.platform.data.web.mapping.MappingMeta;
import me.bunny.kernel.jave.support.JProvider;
import me.bunny.kernel.jave.support.JResourceFinder;
import me.bunny.kernel.jave.support._package.JAbstractMethodFinder;
import me.bunny.kernel.jave.support._package.JDefaultMethodFilter;
import me.bunny.kernel.jave.support._package.JMethodInfoProvider;
import me.bunny.kernel.jave.support._package.JMethodOnSingleClassFinder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

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
		methodFinder.setMethodInfo(mappingMetaInfoGen);
		methodFinder.setMethodFilter(new JDefaultMethodFilter(){
			@Override
			public boolean filter(Method method, Class<?> classIncudeMethod) {
				boolean filter= super.filter(method, classIncudeMethod);
				Annotation annotation=method.getAnnotation(RequestMapping.class);
				return filter||annotation==null;
			}
		});
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
