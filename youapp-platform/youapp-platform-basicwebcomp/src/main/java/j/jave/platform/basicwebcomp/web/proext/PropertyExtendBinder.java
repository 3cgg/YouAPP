package j.jave.platform.basicwebcomp.web.proext;

import j.jave.kernal.jave.identifier.DataBinder;
import j.jave.kernal.jave.support.detect.JDefaultFieldFilter;
import j.jave.kernal.jave.support.detect.JFieldInfoProvider.JFieldInfoGen;
import j.jave.kernal.jave.support.detect.JFieldOnSingleClassDetector;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.platform.basicwebcomp.web.proext.annotation.CodeExtend;
import j.jave.platform.basicwebcomp.web.proext.annotation.ObjectExtend;
import j.jave.platform.basicwebcomp.web.proext.annotation.PropertyExtend;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public abstract class PropertyExtendBinder implements DataBinder {
	
	private static CodeExtendBinder codeExtendBinder=new CodeExtendBinder();
	
	private static ObjectExtendBinder objectExtendBinder=new ObjectExtendBinder();
	
	private static PropertyExtendBinder defaultPropertyExtendBinder=new PropertyExtendBinder() {
		
		@Override
		protected void doBind(PropertyExtendModel propertyExtendModel) {
			PropertyExtend propertyExtend=propertyExtendModel.getPropertyExtend();
			if(propertyExtend.codeExtend()!=null){
				codeExtendBinder.bind(propertyExtendModel);
			}
			else if(propertyExtend.objectExtend()!=null){
				objectExtendBinder.bind(propertyExtendModel);
			}
		}
	};
	
	private static JDefaultFieldFilter PROPERTY_EXTENDALE_FIELD_FILTER=
			new JDefaultFieldFilter(){
		
		public boolean filter(java.lang.Class<?> clazz) {
			return !PropertyExtendable.class.isAssignableFrom(clazz);
		};
		
		@Override
		public boolean filter(Field field, Class<?> classIncudeField) {
			boolean filter=false;
			filter= super.filter(field, classIncudeField);
			if(filter){
				return true;
			}
			boolean needPropertyExtend=false;
			
			PropertyExtend propertyExtend=field.getAnnotation(PropertyExtend.class);
			if(propertyExtend!=null){
				needPropertyExtend=needPropertyExtend||(
						(propertyExtend!=null&&propertyExtend.active())
						&&(
						(propertyExtend.codeExtend()!=null&&propertyExtend.active())
						||(propertyExtend.objectExtend()!=null&&propertyExtend.active())
						));
			}
			
			if(!needPropertyExtend){
				CodeExtend codeExtend=field.getAnnotation(CodeExtend.class);
				needPropertyExtend=needPropertyExtend||(codeExtend!=null&&codeExtend.active());
			}
			
			if(!needPropertyExtend){
				ObjectExtend objectExtend=field.getAnnotation(ObjectExtend.class);
				needPropertyExtend=needPropertyExtend||(objectExtend!=null&&objectExtend.active());
			}
			return !needPropertyExtend;
		}
	};
	
	
	
	private static JFieldInfoGen<PropertyExtendModel> PROPERTY_EXTENDALE_FIELD_GEN=
			new JFieldInfoGen<PropertyExtendModel>() {
				
				@Override
				public PropertyExtendModel getInfo(Field field, Class<?> classIncudeField) {
					
					PropertyExtendModel propertyExtendModel=new PropertyExtendModel();
					propertyExtendModel.setField(field);
					//propertyExtendModel.setObject(object);
					
					Annotation annotation=field.getAnnotation(PropertyExtend.class);
					if(annotation!=null){
						propertyExtendModel.setPropertyExtend((PropertyExtend) annotation);
					}
					
					if(annotation==null){
						annotation=field.getAnnotation(CodeExtend.class);
						if(annotation!=null){
							propertyExtendModel.setCodeExtend((CodeExtend) annotation);
						}
					}
					
					if(annotation==null){
						annotation=field.getAnnotation(ObjectExtend.class);
						if(annotation!=null){
							propertyExtendModel.setObjectExtend((ObjectExtend)annotation);
						}
					}
					
					return propertyExtendModel;
				}
			};
	
			
	public static Object bind(Object object){
		
		if(PropertyExtendable.class.isInstance(object)){
			//can extend property
			JFieldOnSingleClassDetector<PropertyExtendModel> fieldOnSingleClassDetector
			= new JFieldOnSingleClassDetector<PropertyExtendModel>(PROPERTY_EXTENDALE_FIELD_FILTER,PROPERTY_EXTENDALE_FIELD_GEN);
			fieldOnSingleClassDetector.detect(object.getClass());
			List<PropertyExtendModel> defaultFieldMetas= fieldOnSingleClassDetector.getFieldInfos();
			if(JCollectionUtils.hasInCollect(defaultFieldMetas)){
				for(PropertyExtendModel propertyExtendModel: defaultFieldMetas){
					propertyExtendModel.setObject(object);
					
					if(propertyExtendModel.getPropertyExtend()!=null){
						defaultPropertyExtendBinder.doBind(propertyExtendModel);
					}
					else if(propertyExtendModel.getCodeExtend()!=null){
						codeExtendBinder.doBind(propertyExtendModel);
					}
					else if(propertyExtendModel.getObjectExtend()!=null){
						objectExtendBinder.doBind(propertyExtendModel);
					}
				}
			}
		}
		return object;
	}
	
	protected abstract void doBind(PropertyExtendModel propertyExtendModel);
	
	
	
}
