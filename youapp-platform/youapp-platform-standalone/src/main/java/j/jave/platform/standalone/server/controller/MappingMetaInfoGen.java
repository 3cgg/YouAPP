package j.jave.platform.standalone.server.controller;

import j.jave.kernal.jave.support.detect.JMethodInfoProvider.JMethodInfoGen;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MappingMetaInfoGen implements JMethodInfoGen<MappingMeta> {

	private ClassLoader classLoader;
	
	public MappingMetaInfoGen(ClassLoader classLoader) {
		this.classLoader=classLoader;
	}

	@Override
	public MappingMeta getInfo(Method method, Class<?> classIncudeMethod) {
		
		MappingMeta resourceInfo=new MappingMeta();
		resourceInfo.setClazz(classIncudeMethod);
		
		if(!ControllerService.class.isAssignableFrom(classIncudeMethod)){
			throw new IllegalStateException(" class not represented by "+ControllerService.class);
		}
		resourceInfo.setMethodName(method.getName());
		JRequestMapping classRequestMapping= classIncudeMethod.getAnnotation(JRequestMapping.class);
		JRequestMapping methodRequestMapping= method.getAnnotation(JRequestMapping.class);
		
		String[] methodPaths= methodRequestMapping.path();
		String path="";
		if(classRequestMapping!=null){
			String[] classPaths=classRequestMapping.path();
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
