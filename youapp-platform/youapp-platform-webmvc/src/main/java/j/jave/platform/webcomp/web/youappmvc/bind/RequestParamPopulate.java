package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support.JDataBinder;
import j.jave.kernal.jave.support.dataconvert.JDataConvertor;
import j.jave.kernal.jave.support.detect.JDefaultFieldMeta;
import j.jave.kernal.jave.support.detect.JFieldOnSingleClassFinder;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class RequestParamPopulate implements JDataBinder {

	private String prefix;
	
	private HttpContext httpContext;
	
	public RequestParamPopulate(String prefix,HttpContext httpContext){
		this.prefix=prefix;
		this.httpContext=httpContext;
	}
	
	public void bind(final Object object) throws Exception {
		final JDataConvertor dataConvertor=JDataConvertor.build(JConfiguration.get());
		JFieldOnSingleClassFinder<JDefaultFieldMeta> fieldOnSingleClassFinder
		=new JFieldOnSingleClassFinder<JDefaultFieldMeta>(object.getClass());
		List<JDefaultFieldMeta> defaultFieldMetas=fieldOnSingleClassFinder.find().getFieldInfos();
		JCollectionUtils.each(defaultFieldMetas, new JCollectionUtils.CollectionCallback<JDefaultFieldMeta>() {
			@Override
			public void process(JDefaultFieldMeta fieldMeta) throws Exception {
				
				Field field=fieldMeta.getField();
				Class<?> type=field.getType();
				if(JClassUtils.isSimpleType(type)||JClassUtils.isSimpleTypeArray(type)){
					Object value=null;
					if(JStringUtils.isNotNullOrEmpty(prefix)){
						value=httpContext.getParameterValue(getKey(fieldMeta.getFieldName()));
					}
					if(value==null){
						value=httpContext.getParameterValue(fieldMeta.getFieldName());
					}
					if(value!=null){
						if(JClassUtils.isSimpleTypeArray(type)){
							if(!value.getClass().isArray()){
								value=new Object[]{value};
							}
						}
						field.set(object, dataConvertor.convert(type, value));
					}
				}
				else if(JClassUtils.isAssignable(Map.class, type)){
					
				}
				else{
					// ignore ...  may encounter cycle error
//					Object obj=JClassUtils.newObject(type);
//					String innerPrefix=JStringUtils.isNotNullOrEmpty(prefix)?getKey(field.getName()):field.getName();
//					RequestParamPopulate requestParamPopulate=new RequestParamPopulate(innerPrefix, httpContext);
//					requestParamPopulate.populate(obj);
//					field.set(object, obj);
				}
				
			}
		});
	}
	
	
	
	private String getKey(String fieldName){
		return prefix+"."+fieldName;
	}
	
}
