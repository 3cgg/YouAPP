package j.jave.platform.basicwebcomp.web.youappmvc.jsonview;

import j.jave.kernal.jave.support.detect.JDefaultFieldFilter;
import j.jave.kernal.jave.support.detect.JDefaultFieldMeta;
import j.jave.kernal.jave.support.detect.JFieldOnSingleClassDetector;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.proext.PropertyExtendable;
import j.jave.platform.basicwebcomp.web.proext.annotation.PropertyExtend;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class SimplePropertyExtendHandler implements DataModifyHandler {

	private static JDefaultFieldFilter PROPERTY_EXTENDALE_FIELD_FILTER=
			new JDefaultFieldFilter(){
		@Override
		public boolean filter(Field field, Class<?> classIncudeField) {
			boolean filter=false;
			filter= super.filter(field, classIncudeField);
			PropertyExtend propertyExtend=field.getAnnotation(PropertyExtend.class);
			filter=filter&&(propertyExtend!=null&&propertyExtend.extend()!=null&&propertyExtend.active());
			return filter;
		}
	};
	
	@Override
	public void handle(ResponseModel responseModel) {
		Object object=responseModel.getData();
		if(PropertyExtendable.class.isInstance(object)){
			//can extend property
			JFieldOnSingleClassDetector<JDefaultFieldMeta> fieldOnSingleClassDetector
			= new JFieldOnSingleClassDetector<JDefaultFieldMeta>(PROPERTY_EXTENDALE_FIELD_FILTER);
			fieldOnSingleClassDetector.detect(object.getClass());
			List<JDefaultFieldMeta> defaultFieldMetas= fieldOnSingleClassDetector.getFieldInfos();
			if(JCollectionUtils.hasInCollect(defaultFieldMetas)){
				for(JDefaultFieldMeta defaultFieldMeta: defaultFieldMetas){
					Annotation[]  annotations= defaultFieldMeta.getAnnotations();
					PropertyExtend propertyExtend=null;
					for(Annotation annotation:annotations){
						if(PropertyExtend.class.isInstance(annotation)){
							propertyExtend=(PropertyExtend) annotation;
							break;
						}
					}
					if(propertyExtend!=null){
						j.jave.platform.basicwebcomp.web.proext.annotation.PropertyExtendable  
						extendable=propertyExtend.extend();
					}
				}
			}
		}
	}

}
