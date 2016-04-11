package j.jave.kernal.jave.support.databind.proext;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support.databind.DataBindException;
import j.jave.kernal.jave.utils.JStringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SimplePropertyExtendBinder implements PropertyExtendBinder {

	private int potentialCount;
	private int maxCaculCount=Integer.MAX_VALUE;
	
	
	@Override
	public void bind(Object object) {
		getHandler(object).doBind(object);
	}
	
	protected void doBind(Object obj){
		potentialCount++;
		if(potentialCount>maxCaculCount){
			throw new DataBindException("the object is larger...,the data bind occurs this<-");
		}
	}
	
	private static final MapPropertyHandler DO_MAP_PROPERTY_HANDLER=new MapPropertyHandler();
	
	static class MapPropertyHandler extends SimplePropertyExtendBinder{
		
		@SuppressWarnings("rawtypes")
		@Override
		protected void doBind(Object object) {
			super.doBind(object);
			Map map=(Map) object;
			for(Object obj: map.values()){
				getHandler(obj).doBind(obj);
			}
		}
	}
	
	private static final CollectionPropertyHandler DO_COLLECTION_PROPERTY_HANDLER=new CollectionPropertyHandler();
	static class CollectionPropertyHandler extends SimplePropertyExtendBinder{
		@SuppressWarnings("rawtypes")
		@Override
		protected void doBind(Object object) {
			super.doBind(object);
			Collection collection=(Collection) object;
			for(Object obj: collection){
				getHandler(obj).doBind(obj);
			}
		}
	}
	
	private static final SimplePropertyExtendBinder DO_NOTHING_PROPERTY_EXTEND_HANDLER=
			new SimplePropertyExtendBinder(){
		protected void doBind(Object obj) {};
	};
	
	private static List<PropertyExtendHandler> propertyExtendHandlers=new ArrayList<PropertyExtendHandler>();
	
	static{
		propertyExtendHandlers.add(new DateFormatExtendHandler());
		String proExtClassesString=JConfiguration.get().getString(JProperties.DATABIND_PROPERTY_EXT_HANDLER, DateFormatExtendHandler.class.getName());
		String[] proExtClassStrings= JStringUtils.toStringArray(proExtClassesString, ",");
		for(String proExtClassString:proExtClassStrings){
			Class<?> clazz=JClassUtils.load(proExtClassString.trim());
			boolean exists=false;
			for(PropertyExtendHandler propertyExtendHandler:propertyExtendHandlers){
				if(clazz==propertyExtendHandler.getClass()){
					exists=true;
					break;
				}
			}
			if(!exists){
				propertyExtendHandlers.add((PropertyExtendHandler) JClassUtils.newObject(clazz));
			}
		}
	}
	
	private static final SimplePropertyExtendBinder DO_BIND_PROPERTY_EXTEND_HANDLER=
			new SimplePropertyExtendBinder(){
		protected void doBind(Object obj) {
			try{
				super.doBind(obj);
				List<Field> fields=JClassUtils.getFields(obj.getClass(), true, Modifier.PRIVATE,Modifier.PUBLIC);
				for(Field field:fields){
					field.setAccessible(true);
					Annotation[] annotations= field.getAnnotations();
					if(annotations.length>0){
						for(PropertyExtendHandler propertyExtendHandler:SimplePropertyExtendBinder.propertyExtendHandlers){
							if(propertyExtendHandler.accept(field, obj)){
								propertyExtendHandler.handle(field, obj);
							}
						}
					}
					Object inObj=field.get(obj);
					getHandler(inObj).doBind(inObj);
				}
			}catch(Exception e){
				throw new DataBindException(e);
			}
		}
	};
	
	static SimplePropertyExtendBinder getHandler(Object object){
		
		if(PropertyExtendable.class.isInstance(object)){
			return DO_BIND_PROPERTY_EXTEND_HANDLER;
		}
		if(Map.class.isInstance(object)){
			return DO_MAP_PROPERTY_HANDLER;
		}
		
		if(Collection.class.isInstance(object)){
			return DO_COLLECTION_PROPERTY_HANDLER;
		}
		return DO_NOTHING_PROPERTY_EXTEND_HANDLER;
	}
	
	
	
	
}
