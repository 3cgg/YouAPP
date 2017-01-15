package j.jave.platform.webcomp.web.util;

import j.jave.platform.webcomp.WebCompProperties;
import j.jave.platform.webcomp.web.youappmvc.controller.ControllerSupport;
import me.bunny.app._c.data.web.mapping.MappingMeta;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.reflect.JClassUtils;
import me.bunny.kernel._c.support.JProvider;
import me.bunny.kernel._c.support.JResourceFinder;
import me.bunny.kernel._c.support._package.JAbstractMethodFinder;
import me.bunny.kernel._c.support._package.JMethodInfoProvider;
import me.bunny.kernel._c.support._package.JMethodOnClassPathFinder;
import me.bunny.kernel._c.support._package.JAbstractMethodFinder.JMethodFilter;
import me.bunny.kernel._c.support._package.JMethodInfoProvider.JMethodInfoGen;

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
public class OnClasspathMappingFinder implements JProvider, JResourceFinder<OnClasspathMappingFinder> {
	
	private JAbstractMethodFinder<MappingMeta> methodFinder;
	
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
	
	public OnClasspathMappingFinder(ClassLoader classLoader){
		this(JConfiguration.get(),classLoader);
	}
	
	public OnClasspathMappingFinder(JConfiguration configuration,ClassLoader classLoader){
		this.configuration=configuration;
		this.classLoader=classLoader;
	}

	private void clean(){
		String superClassString=configuration.getString(WebCompProperties.CONTROLLER_SUPER_CLASS, ControllerSupport.class.getName());
		superClass=JClassUtils.load(superClassString);
		methodInfo=new MappingMetaInfoGen(classLoader);
		String controllerPackage=configuration.getString(WebCompProperties.CONTROLLER_PACKAGE, "j.jave");
		String[] includePacks=controllerPackage.split(";");
		methodFinder=new JMethodOnClassPathFinder(superClass);
		methodFinder.setIncludePackages(includePacks);
		methodFinder.setMethodFilter(methodFilter);
		methodFinder.setMethodInfo(methodInfo);
	}
	
//	private volatile boolean flag=true;
//	private final Object lock=new Object();
	
	/**
	 * get all resource informations fist time, 
	 * the same one returned if call subsequently.
	 * @return
	 */
	public synchronized OnClasspathMappingFinder find(){
		clean();
		methodFinder.find();
		return this;
	}
	
	@Override
	public OnClasspathMappingFinder cache() {
		return this;
	}
	
	
	/**
	 * force refresh the resources. scan and wrap resources every time , 
	 * a new {@link JMethodInfoProvider} returned every time. 
	 */
	public synchronized OnClasspathMappingFinder refresh(){
		clean();
		methodFinder.refresh();
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
