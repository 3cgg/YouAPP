package j.jave.platform.basicwebcomp.web.youappmvc.controller;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.SessionUser;
import j.jave.platform.basicwebcomp.web.util.ClassProvidedMappingDetector;
import j.jave.platform.basicwebcomp.web.util.MappingMeta;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.service.PageableService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * basic action for view controller.
 * @author J
 */
public abstract class ControllerSupport implements YouappController,InitializingBean {
	
	protected final Logger LOGGER=LoggerFactory.getLogger(getClass());
	
	public static final ThreadLocal<HttpContext> httpContext=new ThreadLocal<HttpContext>();
	
	protected PageableService pageableService=JServiceHubDelegate.get().getService(this, PageableService.class);
	
//	/**
//	 * {@link #setHttpContext(HttpContext)}
//	 */
//	public static final String httpContextCallBackName="setHttpContext";
	
	public static final String CREATE_SUCCESS="保存成功";
	public static final String DELETE_SUCCESS="删除成功";
	public static final String EDIT_SUCCESS="更新成功";

	public void removeHttpContext(){
		httpContext.remove();
	}
	
	public HttpContext getHttpContext() {
		return httpContext.get();
	}

	public void setHttpContext(HttpContext httpContext) {
		ControllerSupport.httpContext.set(httpContext);
	}
	
	protected void setAttribute(String key,Object obj){
		httpContext.get().setAttribute(key, obj);
	}
	
	public String getParameter(String key){
		return httpContext.get().getParameter(key);
	}
	
	public void setCookie(String key,String value){
		httpContext.get().setCookie(key, value);
	}
	
	public void setCookie(String key,String value,int maxAge){
		httpContext.get().setCookie(key, value, maxAge);
	}
	
	public void deleteCookie(String key){
		httpContext.get().deleteCookie(key);
	}
	
	protected SessionUser getSessionUser(){
		return httpContext.get().getUser();
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
	
	public ServiceContext getServiceContext(){
		return new ServiceContext();
	}

    @Override
    public final void afterPropertiesSet() throws Exception {

    	ClassProvidedMappingDetector mappingDetector=new ClassProvidedMappingDetector(getClass());
		mappingDetector.detect();
		List<MappingMeta> mappingMetas= mappingDetector.getMappingMetas();
		for(MappingMeta meta:mappingMetas){
			MappingController.putMappingMeta(meta.getPath(),meta);
			MappingController.putControllerObject(meta.getPath(),this);
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
