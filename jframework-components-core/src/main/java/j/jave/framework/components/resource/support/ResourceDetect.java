package j.jave.framework.components.resource.support;

import j.jave.framework.components.web.action.Action;
import j.jave.framework.support.detect.JMethodDetect;
import j.jave.framework.support.detect.JMethodDetect.JMethodFilter;
import j.jave.framework.support.detect.JMethodInfoProvider;
import j.jave.framework.support.detect.JMethodInfoProvider.JMethodInfo;
import j.jave.framework.support.detect.JResourceDetect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.stereotype.Controller;

/**
 * detect all resources , wrap the information via {@link ResourceInfo}
 * <p>its thread safely
 * @see {@link JMethodInfoProvider}
 * @author J
 */
public class ResourceDetect implements JResourceDetect<JMethodInfoProvider<ResourceInfo>> {
	final JMethodDetect<ResourceInfo> methodDetect;
	
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
			// if static method.
			isFilter=Modifier.isStatic(method.getModifiers())
					||Modifier.isNative(method.getModifiers())
					;
			
			return isFilter;
		}
		
		@Override
		public int[] methodModifiers() {
			return new int[]{Modifier.PUBLIC};
		}
	};
	
	static final JMethodInfo<ResourceInfo> methodInfo=new JMethodInfo<ResourceInfo>() {
		@Override
		public ResourceInfo getInfo(Method method, Class<?> classIncudeMethod) {
			
			ResourceInfo resourceInfo=new ResourceInfo();
			resourceInfo.setClazz(classIncudeMethod);
			
			Controller controller=classIncudeMethod.getAnnotation(Controller.class);
			if(controller!=null){
				resourceInfo.setControllerName(controller.value());
			}
			else{
				throw new IllegalStateException(" class not represented by "+Controller.class);
			}
			resourceInfo.setMethodName(method.getName());
			resourceInfo.setPath("/"+resourceInfo.getControllerName()+"/"+resourceInfo.getMethodName());
			
			return resourceInfo;
		}
	};
	
	public ResourceDetect(){
		methodDetect=new JMethodDetect<ResourceInfo>(methodFilter,methodInfo);
	}
	
	
	private volatile boolean flag=true;
	private final Object lock=new Object();
	
	/**
	 * get all resource informations fist time, 
	 * the same one returned if call subsequently.
	 * @return
	 */
	public JMethodInfoProvider<ResourceInfo> detect(){
		if(flag){
			synchronized (lock) {
				if(flag){
					methodDetect.setIncludePackages(new String[]{"j.jave.framework.components"});
					methodDetect.detect(Action.class, true);
					flag=false;
				}
			}
		}
		return methodDetect;
	}
	
	
	/**
	 * force refresh the resources. scan and wrap resources every time , 
	 * a new {@link JMethodInfoProvider} returned every time. 
	 */
	public JMethodInfoProvider<ResourceInfo> refresh(){
		// force reloading the resources. 
		JMethodDetect<ResourceInfo> methodDetect=new JMethodDetect<ResourceInfo>(methodFilter,methodInfo);
		methodDetect.setIncludePackages(new String[]{"j.jave.framework.components"});
		methodDetect.detect(Action.class, true);
		return methodDetect;
	}
	
	
}
