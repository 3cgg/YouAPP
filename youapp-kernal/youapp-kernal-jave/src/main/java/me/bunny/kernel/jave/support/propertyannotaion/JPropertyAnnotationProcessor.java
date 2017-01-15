package me.bunny.kernel.jave.support.propertyannotaion;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.support.databind.JDataBindingException;

public class JPropertyAnnotationProcessor {

	private int potentialCount;
	private int maxCaculCount=Integer.MAX_VALUE;
	
	public final void handle(Object object) {
		getHandler(object).doHandle(object);
	}
	
	void doHandle(Object obj){
		potentialCount++;
		if(potentialCount>maxCaculCount){
			throw new JDataBindingException("the object is larger...,the data handleing occurs this<-");
		}
	}
	
	private MapPropertyHandler DO_MAP_PROPERTY_HANDLER;
	
	class MapPropertyHandler extends JPropertyAnnotationProcessor{
		
		@SuppressWarnings("rawtypes")
		@Override
		protected void doHandle(Object object) {
			super.doHandle(object);
			Map map=(Map) object;
			for(Object obj: map.values()){
				getHandler(obj).doHandle(obj);
			}
		}
	}
	
	private CollectionPropertyHandler DO_COLLECTION_PROPERTY_HANDLER;
	
	class CollectionPropertyHandler extends JPropertyAnnotationProcessor{
		@SuppressWarnings("rawtypes")
		@Override
		protected void doHandle(Object object) {
			super.doHandle(object);
			Collection collection=(Collection) object;
			for(Object obj: collection){
				getHandler(obj).doHandle(obj);
			}
		}
	}
	
	private JPropertyAnnotationProcessor DO_NOTHING_PROPERTY_EXTEND_HANDLER;
	
	class NothingHandler extends JPropertyAnnotationProcessor{
		protected void doHandle(Object obj) {};
	}
	
	
	public static JPropertyAnnotationProcessor build(JPropertyAnnotationConfig config){
		JPropertyAnnotationProcessor annotationProcessor=new JPropertyAnnotationProcessor();
		annotationProcessor.propertyAnnotationHandlers=config.getPropertyAnnotationHandlers();
		annotationProcessor.classIdentifiers=config.getClassIdentifiers();
		annotationProcessor.DO_MAP_PROPERTY_HANDLER=annotationProcessor.new MapPropertyHandler();
		annotationProcessor.DO_COLLECTION_PROPERTY_HANDLER=annotationProcessor.new CollectionPropertyHandler();
		annotationProcessor.DO_NOTHING_PROPERTY_EXTEND_HANDLER=annotationProcessor.new NothingHandler();
		annotationProcessor.DO_PROPERTY_EXTEND_HANDLER=annotationProcessor.new DoHandler();
		return annotationProcessor;
	}
	
	@SuppressWarnings("unchecked")
	private List<? extends JPropertyAnnotationHandler> propertyAnnotationHandlers=Collections.EMPTY_LIST;
	
	@SuppressWarnings("unchecked")
	private List<Class<?>> classIdentifiers=Collections.EMPTY_LIST;
	
	public final List<? extends JPropertyAnnotationHandler> getPropertyAnnotationHandlers() {
		return propertyAnnotationHandlers;
	}
	
	public List<Class<?>> getClassIdentifiers() {
		return classIdentifiers;
	}
	
	private JPropertyAnnotationProcessor DO_PROPERTY_EXTEND_HANDLER;
	
	class DoHandler extends JPropertyAnnotationProcessor{

		protected void doHandle(Object obj) {
			try{
				super.doHandle(obj);
				List<Field> fields=JClassUtils.getFields(obj.getClass(), true, Modifier.PRIVATE,Modifier.PUBLIC);
				for(Field field:fields){
					field.setAccessible(true);
					Annotation[] annotations= field.getAnnotations();
					if(annotations.length>0){
						for(JPropertyAnnotationHandler propertyAnnotationHandler:JPropertyAnnotationProcessor.this.propertyAnnotationHandlers){
							if(propertyAnnotationHandler.accept(field, obj)){
								propertyAnnotationHandler.handle(field, obj);
							}
						}
					}
					Object inObj=field.get(obj);
					getHandler(inObj).doHandle(inObj);
				}
			}catch(Exception e){
				throw new JDataBindingException(e);
			}
		}
	
	}
	
	
	private boolean matches(Object object){
		boolean matches=false;
		for(Class<?> clazz:classIdentifiers){
			if(clazz.isInstance(object)){
				matches=true;
				break;
			}
		}
		return matches;
	}
	
	private JPropertyAnnotationProcessor getHandler(Object object){
		if(Map.class.isInstance(object)){
			return DO_MAP_PROPERTY_HANDLER;
		}
		if(Collection.class.isInstance(object)){
			return DO_COLLECTION_PROPERTY_HANDLER;
		}
		if(matches(object)){
			return DO_PROPERTY_EXTEND_HANDLER;
		}
		return DO_NOTHING_PROPERTY_EXTEND_HANDLER;
	}
}
