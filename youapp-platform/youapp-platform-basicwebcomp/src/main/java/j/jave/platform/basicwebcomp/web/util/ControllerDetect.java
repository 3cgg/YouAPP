package j.jave.platform.basicwebcomp.web.util;

import j.jave.kernal.jave.support.detect.JMethodDetect;
import j.jave.kernal.jave.support.detect.JMethodInfoProvider;
import j.jave.kernal.jave.support.detect.JResourceDetect;
import j.jave.kernal.jave.support.detect.JMethodDetect.JMethodFilter;
import j.jave.kernal.jave.support.detect.JMethodInfoProvider.JMethodInfo;
import j.jave.platform.basicwebcomp.web.IWebController;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.stereotype.Controller;

/**
 * detect all resources , wrap the information via {@link ControllerInfo}
 * <p>its thread safely
 * @see {@link JMethodInfoProvider}
 * @author J
 */
public class ControllerDetect implements JResourceDetect<JMethodInfoProvider<ControllerInfo>> {
	final JMethodDetect<ControllerInfo> methodDetect;
	
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
			
			return isFilter;
		}
		
		@Override
		public int[] methodModifiers() {
			return new int[]{Modifier.PUBLIC};
		}
	};
	
	static final JMethodInfo<ControllerInfo> methodInfo=new JMethodInfo<ControllerInfo>() {
		@Override
		public ControllerInfo getInfo(Method method, Class<?> classIncudeMethod) {
			
			ControllerInfo resourceInfo=new ControllerInfo();
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
	
	public ControllerDetect(){
		methodDetect=new JMethodDetect<ControllerInfo>(methodFilter,methodInfo);
	}
	
	
	private volatile boolean flag=true;
	private final Object lock=new Object();
	
	/**
	 * get all resource informations fist time, 
	 * the same one returned if call subsequently.
	 * @return
	 */
	public JMethodInfoProvider<ControllerInfo> detect(){
		if(flag){
			synchronized (lock) {
				if(flag){
					methodDetect.setIncludePackages(new String[]{"j.jave.framework.components"});
					methodDetect.detect(IWebController.class, true);
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
	public JMethodInfoProvider<ControllerInfo> refresh(){
		// force reloading the resources. 
		JMethodDetect<ControllerInfo> methodDetect=new JMethodDetect<ControllerInfo>(methodFilter,methodInfo);
		methodDetect.setIncludePackages(new String[]{"j.jave.framework.components"});
		methodDetect.detect(IWebController.class, true);
		return methodDetect;
	}
	
	
}
