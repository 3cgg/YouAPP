package j.jave.platform.basicwebcomp.web.support;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.basicwebcomp.web.util.ClassProvidedMappingDetector;
import j.jave.platform.basicwebcomp.web.util.MappingMeta;
import j.jave.platform.basicwebcomp.web.youappmvc.action.MappingController;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

/**
 * Created by J on 2016/3/12.
 */
public class ControllerSupport implements InitializingBean {
	private JLogger logger=JLoggerFactory.getLogger(getClass());

    @Override
    public void afterPropertiesSet() throws Exception {

    	ClassProvidedMappingDetector mappingDetector=new ClassProvidedMappingDetector(getClass());
		mappingDetector.detect();
		List<MappingMeta> mappingMetas= mappingDetector.getMappingMetas();
		for(MappingMeta meta:mappingMetas){
			MappingController.putMappingMeta(meta.getPath(),meta);
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
