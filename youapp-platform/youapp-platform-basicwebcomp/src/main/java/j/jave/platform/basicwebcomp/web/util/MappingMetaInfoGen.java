package j.jave.platform.basicwebcomp.web.util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import j.jave.kernal.jave.support.detect.JMethodInfoProvider.JMethodInfoGen;
import j.jave.platform.basicsupportcomp.core.container.MappingMeta;
import j.jave.platform.basicsupportcomp.core.container.MethodParamMeta;

public class MappingMetaInfoGen implements JMethodInfoGen<MappingMeta> {

	private ClassLoader classLoader;
	
	public MappingMetaInfoGen(ClassLoader classLoader) {
		this.classLoader=classLoader;
	}

	@Override
	public MappingMeta getInfo(Method method, Class<?> classIncudeMethod) {
		
		MappingMeta resourceInfo=new MappingMeta();
		resourceInfo.setClazz(classIncudeMethod);
		
		Controller controller=classIncudeMethod.getAnnotation(Controller.class);
		if(controller!=null){
			resourceInfo.setControllerName(controller.value());
		}
		else{
			throw new IllegalStateException(" class not represented by "+Controller.class);
		}
		resourceInfo.setMethodName(method.getName());
		RequestMapping classRequestMapping= classIncudeMethod.getAnnotation(RequestMapping.class);
		RequestMapping methodRequestMapping= method.getAnnotation(RequestMapping.class);
		
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
		String[] parameterNames=ParameterNameGet.getMethodParameterNamesByAsm4(classIncudeMethod, method,classLoader);
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
		return resourceInfo;
	}

	
}