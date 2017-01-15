package j.jave.platform.webcomp.web.youappmvc.controller;

import j.jave.platform.data.web.mapping.MappingMeta;
import j.jave.platform.data.web.model.JInputModel;
import j.jave.platform.webcomp.core.service.ServiceContext;
import me.bunny.kernel._c.model.JSimplePageable;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * basic action for view controller.
 * @author J
 */
public class SimpleControllerSupport extends ControllerSupport{

	@Override
	protected void validate(MappingMeta mappingMeta) {

		
		Annotation[] annotations=mappingMeta.getAnnotations();
		if(annotations.length>0){
			for(Annotation annotation:annotations){
				if(SkipMappingCheck.class==annotation.annotationType()){
					return ;
				}
			}
		}
		
		
		 Class<?>[] methodParamClasses = mappingMeta.getMethodParamClasses();
         Object[] parameters = new Object[methodParamClasses.length];
         boolean validate=true;
         if(validate){
         	String errorMessage="the method argument is invlaid :"+mappingMeta.getPath();
             if(methodParamClasses.length==0){
             	//throw new RuntimeException(errorMessage);
             }
             if(methodParamClasses.length>0){
             	if(ServiceContext.class!=methodParamClasses[0]){
             		//throw new RuntimeException(errorMessage);
             	}
             	
             	if(parameters.length==1){
             		//do validation.
             		Class<?> clazz=methodParamClasses[0];
             		if(!(JSimplePageable.class==clazz
             				||isAcceptedSimpleClass(clazz)
             				||isCompositeClass(clazz)
             				||JInputModel.class.isAssignableFrom(clazz))){
             			throw new RuntimeException(errorMessage);
             		}
             	}
             	
             	if(parameters.length==2){
             		//do validation.
             		Class<?> clazz=methodParamClasses[0];
             		if(!(JInputModel.class.isAssignableFrom(clazz)
             				||isCompositeClass(clazz)
             				||isAcceptedSimpleClass(clazz))){
             			throw new RuntimeException(errorMessage);
             		}
             		
             		clazz=methodParamClasses[1];
             		if(JSimplePageable.class!=clazz){
             			throw new RuntimeException(errorMessage);
             		}
             	}
             	
             }
         }
	
	}
	
	
	
	public static boolean isCompositeClass(Class<?> clazz){
		if(clazz.isArray()
				||List.class.isAssignableFrom(clazz)
				||Map.class.isAssignableFrom(clazz)){
			return true;
		}
		return false;
	}
	
	public static boolean isAcceptedSimpleClass(Class<?> clazz){
		if(String.class==clazz
				||Byte.class==clazz
				||Short.class==clazz
				||Integer.class==clazz
				||Long.class==clazz
				||Float.class==clazz
				||Double.class==clazz
				||BigDecimal.class==clazz
				||byte.class==clazz
				||short.class==clazz
				||int.class==clazz
				||long.class==clazz
				||float.class==clazz
				||double.class==clazz
				){
			return true;
		}
		return false;
	}
	
	
	public Object getBeanObject(){
		return applicationContext.getBean(beanName);
	}
	
	
}
