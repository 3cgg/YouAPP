package j.jave.platform.webcomp.web.youappmvc.controller;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.model.JPageable;
import j.jave.platform.data.web.mapping.MappingMeta;
import j.jave.platform.sps.core.SpringDynamicJARApplicationContext;
import j.jave.platform.sps.core.context.SpringContextSupport;
import j.jave.platform.webcomp.web.util.ClassProvidedMappingFinder;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.HttpContextHolder;
import j.jave.platform.webcomp.web.youappmvc.container.HttpInvokeContainerDelegateService;
import j.jave.platform.webcomp.web.youappmvc.service.PageableService;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * basic action for view controller.
 * @author J
 */
public abstract class ControllerSupport implements YouappController,InitializingBean,ApplicationContextAware
,BeanNameAware{
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	protected PageableService pageableService=JServiceHubDelegate.get().getService(this, PageableService.class);
	
	private ApplicationContext applicationContext;
	
	private String beanName;
	
	private HttpInvokeContainerDelegateService requestInvokeContainerDelegate=
			JServiceHubDelegate.get().getService(this,HttpInvokeContainerDelegateService.class);
	
	@Override
	public final void setBeanName(String name) {
		this.beanName=name;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
//	/**
//	 * {@link #setHttpContext(HttpContext)}
//	 */
//	public static final String httpContextCallBackName="setHttpContext";
	
	public static final String CREATE_SUCCESS="保存成功";
	public static final String DELETE_SUCCESS="删除成功";
	public static final String UPDATE_SUCCESS="更新成功";
	
	public HttpContext getHttpContext() {
		return HttpContextHolder.get();
	}
	
	public String getParameter(String key){
		return getHttpContext().getParameter(key);
	}
	
	/**
	 * sub-class should implements the method.
	 * @return
	 */
	protected JPageable parseJPage(){
		throw new JOperationNotSupportedException("Not supported,check if the sub-class implements the method.");
	} 
	
    @Override
    public final void afterPropertiesSet() throws Exception {

    	boolean isDynamicLoader=SpringDynamicJARApplicationContext.class.isInstance(applicationContext);
    	String unique=SpringContextSupport.PLATFORM;
    	String prefix="";
    	if(isDynamicLoader){
    		SpringDynamicJARApplicationContext springDynamicJARApplicationCotext=(SpringDynamicJARApplicationContext)applicationContext;
    		unique=springDynamicJARApplicationCotext.getUnique();
    		prefix=springDynamicJARApplicationCotext.getComponentVersionApplication().getUrlPrefix();
    	}
    	
    	ClassProvidedMappingFinder mappingFinder=new ClassProvidedMappingFinder(getClass());
		List<MappingMeta> mappingMetas= mappingFinder.find().getMappingMetas();
		for(MappingMeta meta:mappingMetas){
			meta.setControllerName(beanName);
			if(!meta.getPath().startsWith(prefix)){
				throw new JInitializationException("the request mapping of controller ["+this.getClass().getName()
						+"] must be start with "+prefix);
			}
			if(isDynamicLoader){
				SpringDynamicJARApplicationContext springDynamicJARApplicationCotext=(SpringDynamicJARApplicationContext)applicationContext;
				springDynamicJARApplicationCotext.putMappingMeta(meta);
			}
			else{
				requestInvokeContainerDelegate.execute(new URI(HttpInvokeContainerDelegateService.getControllerRequestPutURI(unique, meta.getPath())
				), meta, unique);
			}
		}
    	
    	/*
        Class<?> classIncudeMethod=this.getClass();
        Method[] methods=classIncudeMethod.getMethods();
        if(methods.length>0){
            for (int j = 0; j < methods.length; j++) {
                Method method=methods[j];
                MappingMeta resourceInfo=new MappingMeta();
                resourceInfo.setClazz(classIncudeMethod);

                Controller controller=classIncudeMethod.getAnnotation(Controller.class);
                RequestMapping methodRequestMapping= method.getAnnotation(RequestMapping.class);
                if(methodRequestMapping==null){
                	logger.warn(" method : "+method.getName()+", cannot apply annotation [@RequestMapping] .");
                	continue;
                }
                if(controller!=null){
                    resourceInfo.setControllerName(controller.value());
                }
                else{
                    throw new IllegalStateException(" class not represented by "+Controller.class);
                }
                resourceInfo.setMethodName(method.getName());
                RequestMapping classRequestMapping= classIncudeMethod.getAnnotation(RequestMapping.class);
                
                String[] methodPaths= methodRequestMapping.value();
                String path="";
                if(classRequestMapping!=null){
                    String[] classPaths=classRequestMapping.value();
                    if(classPaths.length>0){
                        path=classPaths[0];
                    }
                }
                if(methodPaths.length>0){
                    path=path+methodPaths[0];
                }
                resourceInfo.setPath(path);
                Parameter[] parameters= method.getParameters();
                String[] parameterNames= ParameterNameGet.getMethodParameterNamesByAsm4(classIncudeMethod, method);
                MethodParamMeta[] methodParamMetas=new MethodParamMeta[parameters.length];
                for(int i=0;i<parameters.length;i++){
                    Parameter parameter=parameters[i];
                    Class<?> clazz=parameter.getType();
                    MethodParamMeta paramMeta=new MethodParamMeta();
                    paramMeta.setType(clazz);
                    paramMeta.setName(parameterNames[i]);
                    paramMeta.setAnnotations(parameter.getAnnotations());
                    paramMeta.setIndex(i);
                    methodParamMetas[i]=paramMeta;
                }
                resourceInfo.setMethodParams(methodParamMetas);
                MappingController.putMappingMeta(resourceInfo.getPath(),resourceInfo);
            }
        }
	*/
    }

	
}
