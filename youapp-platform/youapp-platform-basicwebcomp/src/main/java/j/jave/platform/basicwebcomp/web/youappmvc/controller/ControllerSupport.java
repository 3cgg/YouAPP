package j.jave.platform.basicwebcomp.web.youappmvc.controller;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationContext;
import j.jave.platform.basicsupportcomp.core.container.MappingMeta;
import j.jave.platform.basicsupportcomp.core.context.SpringContextSupport;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.SessionUser;
import j.jave.platform.basicwebcomp.web.util.ClassProvidedMappingDetector;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContextHolder;
import j.jave.platform.basicwebcomp.web.youappmvc.container.HttpInvokeContainerDelegateService;
import j.jave.platform.basicwebcomp.web.youappmvc.service.PageableService;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	protected final Logger LOGGER=LoggerFactory.getLogger(getClass());
	
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
	
	protected void setAttribute(String key,Object obj){
		getHttpContext().setAttribute(key, obj);
	}
	
	public String getParameter(String key){
		return getHttpContext().getParameter(key);
	}
	
	public void setCookie(String key,String value){
		getHttpContext().setCookie(key, value);
	}
	
	public void setCookie(String key,String value,int maxAge){
		getHttpContext().setCookie(key, value, maxAge);
	}
	
	public void deleteCookie(String key){
		getHttpContext().deleteCookie(key);
	}
	
	protected SessionUser getSessionUser(){
		return getHttpContext().getUser();
	}

	protected void setSuccessMessage(String message){
		if(JStringUtils.isNullOrEmpty(message)){
			message="操作成功";
		}
		setAttribute("successAlertMessage", message);
	}
	
	/**
	 * sub-class should implements the method.
	 * @return
	 */
	protected JPageable parseJPage(){
		throw new JOperationNotSupportedException("Not supported,check if the sub-class implements the method.");
	} 
	
	protected ServiceContext getServiceContext(){
		return getHttpContext().getServiceContext();
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
    	
    	ClassProvidedMappingDetector mappingDetector=new ClassProvidedMappingDetector(getClass());
		mappingDetector.detect();
		List<MappingMeta> mappingMetas= mappingDetector.getMappingMetas();
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
