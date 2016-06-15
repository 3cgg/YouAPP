package j.jave.platform.webcomp.web.util;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support.detect.JMethodDetector;
import j.jave.kernal.jave.support.detect.JMethodDetector.JMethodFilter;
import j.jave.kernal.jave.support.detect.JMethodInfoProvider;
import j.jave.kernal.jave.support.detect.JMethodInfoProvider.JMethodInfoGen;
import j.jave.kernal.jave.support.detect.JProvider;
import j.jave.kernal.jave.support.detect.JResourceDetector;
import j.jave.platform.data.web.mapping.MappingMeta;
import j.jave.platform.webcomp.WebCompProperties;
import j.jave.platform.webcomp.web.youappmvc.controller.ControllerSupport;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * detect all resources , wrap the information via {@link MappingMeta}
 * <p>its thread safely
 * @see {@link JMethodInfoProvider}
 * @author J
 */
public class MappingDetector implements JProvider, JResourceDetector<MappingDetector> {
	private JMethodDetector<MappingMeta> methodDetector;
	
	private JConfiguration configuration;
	
	private Class<?> superClass;
	
	private ClassLoader classLoader;
	
	static final JMethodFilter methodFilter=new JMethodFilter() {
		@Override
		public boolean filter(Class<?> clazz) {
			return !clazz.isAnnotationPresent(Controller.class)
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
			Controller controller=method.getDeclaringClass().getAnnotation(Controller.class);
			isFilter=controller==null;
			
			if(!isFilter){
				// if static method.
				isFilter=Modifier.isStatic(method.getModifiers())
						||Modifier.isNative(method.getModifiers())
						;
			}
			isFilter=method.getAnnotation(RequestMapping.class)==null;
			return isFilter;
		}
		
		@Override
		public int[] methodModifiers() {
			return new int[]{Modifier.PUBLIC};
		}
	};
	
	JMethodInfoGen<MappingMeta> methodInfo;
	
	public MappingDetector(ClassLoader classLoader){
		this(JConfiguration.get(),classLoader);
	}
	
	public MappingDetector(JConfiguration configuration,ClassLoader classLoader){
		this.configuration=configuration;
		this.classLoader=classLoader;
		initMethodDetector();
		String superClassString=configuration.getString(WebCompProperties.CONTROLLER_SUPER_CLASS, ControllerSupport.class.getName());
		superClass=JClassUtils.load(superClassString);
	}

	private void initMethodDetector(){
		this.methodInfo=new MappingMetaInfoGen(classLoader);
		methodDetector=new JMethodDetector<MappingMeta>(methodFilter,methodInfo);
		String controllerPackage=configuration.getString(WebCompProperties.CONTROLLER_PACKAGE, "j.jave");
		methodDetector.setIncludePackages(controllerPackage.split(";"));
	}
	
//	private volatile boolean flag=true;
//	private final Object lock=new Object();
	
	/**
	 * get all resource informations fist time, 
	 * the same one returned if call subsequently.
	 * @return
	 */
	public synchronized MappingDetector detect(){
//		if(flag){
//			synchronized (lock) {
//				if(flag){
//					methodDetector.detect(superClass, true);
//					flag=false;
//				}
//			}
//		}
		methodDetector.detect(superClass, true);
		return this;
	}
	
	
	/**
	 * force refresh the resources. scan and wrap resources every time , 
	 * a new {@link JMethodInfoProvider} returned every time. 
	 */
	public synchronized MappingDetector refresh(){
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
