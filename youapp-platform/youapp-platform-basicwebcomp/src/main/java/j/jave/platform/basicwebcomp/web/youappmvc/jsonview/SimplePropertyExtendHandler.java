package j.jave.platform.basicwebcomp.web.youappmvc.jsonview;

import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.proext.PropertyExtendBinder;
import j.jave.platform.basicwebcomp.web.proext.PropertyExtendable;
import j.jave.platform.basicwebcomp.web.proext.annotation.CodeExtend;
import j.jave.platform.basicwebcomp.web.proext.annotation.ObjectExtend;
import j.jave.platform.basicwebcomp.web.proext.annotation.PropertyExtend;
import j.jave.platform.basicwebcomp.web.youappmvc.bind.DataBindException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SimplePropertyExtendHandler implements DataModifyHandler {

	private int potentialCount;
	private int maxCaculCount=Integer.MAX_VALUE;
	
	@Override
	public final void handle(ResponseModel responseModel) {
		Object object=responseModel.getData();
		getHandler(object).doHandle(object);
	}
	
	protected void doHandle(Object obj){
		potentialCount++;
		if(potentialCount>maxCaculCount){
			throw new DataBindException("the object is larger...,the data bind occurs this<-");
		}
	}
	
	private static final MapPropertyHandler DO_MAP_PROPERTY_HANDLER=new MapPropertyHandler();
	
	static class MapPropertyHandler extends SimplePropertyExtendHandler{
		
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
	
	private static final CollectionPropertyHandler DO_COLLECTION_PROPERTY_HANDLER=new CollectionPropertyHandler();
	static class CollectionPropertyHandler extends SimplePropertyExtendHandler{
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
	
	private static final SimplePropertyExtendHandler DO_NOTHING_PROPERTY_EXTEND_HANDLER=
			new SimplePropertyExtendHandler(){
		protected void doHandle(Object obj) {};
	};
	
	private static final SimplePropertyExtendHandler DO_BIND_PROPERTY_EXTEND_HANDLER=
			new SimplePropertyExtendHandler(){
		protected void doHandle(Object obj) {
			try{
				super.doHandle(obj);
				boolean bindThis=false;
				List<Field> fields=JClassUtils.getFields(obj.getClass(), true, Modifier.PRIVATE,Modifier.PUBLIC);
				for(Field field:fields){
					field.setAccessible(true);
					if(field.getAnnotation(CodeExtend.class)!=null
							||field.getAnnotation(PropertyExtend.class)!=null
							||field.getAnnotation(ObjectExtend.class)!=null){
						bindThis=true;
						continue;
					}
					Object inObj=field.get(obj);
					getHandler(inObj).doHandle(inObj);
				}
				if(bindThis){
					PropertyExtendBinder.bind(obj);
				}
			}catch(Exception e){
				throw new DataBindException(e);
			}
		}
	};
	
	static SimplePropertyExtendHandler getHandler(Object object){
		
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
