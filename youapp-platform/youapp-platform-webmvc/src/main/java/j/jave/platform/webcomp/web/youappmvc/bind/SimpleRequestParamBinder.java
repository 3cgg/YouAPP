package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.platform.data.common.MethodParamObject;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.support._package.JDefaultFieldMeta;
import me.bunny.kernel.jave.support._package.JFieldOnSingleClassFinder;
import me.bunny.kernel.jave.support.databind.JDataBindingException;
import me.bunny.kernel.jave.support.parser.JDefaultSimpleDataParser;
import me.bunny.kernel.jave.utils.JCollectionUtils;
import me.bunny.kernel.jave.utils.JStringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class SimpleRequestParamBinder implements MethodParamObjectBinder {

	private String prefix;
	
	private HttpContext httpContext;
	
	public SimpleRequestParamBinder(String prefix,HttpContext httpContext){
		this.prefix=prefix;
		this.httpContext=httpContext;
	}
	
	public void bindObject(final Object object) throws JDataBindingException{
		try{
			final JDefaultSimpleDataParser dataParser=JDefaultSimpleDataParser.build(JConfiguration.get());
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
							JClassUtils.set(field.getName(), dataParser.parse(type, value), object);
						}
					}
					else if(JClassUtils.isAssignable(Map.class, type)){
//						JClassUtils.set(field.getName(), httpContext.getParameters(), object);
					}
					else{ // is bean 
						// bean must initialize itself,
//						String innerPrefix=JStringUtils.isNotNullOrEmpty(prefix)?getKey(field.getName()):field.getName();
//						SimpleRequestParamBinder requestParamBinder=new SimpleRequestParamBinder(innerPrefix, httpContext);
//						requestParamBinder.bindObject(JClassUtils.get(field.getName(), object));
					}
				}
			});
		}catch(Exception e){
			throw new JDataBindingException(e);
		}
	}
	
	public void bind(MethodParamObject methodParamObject) throws JDataBindingException {
		bindObject(methodParamObject.getObject());
	}
	
	private String getKey(String fieldName){
		if(JStringUtils.isNullOrEmpty(prefix)){
			return fieldName;
		}
		return prefix+"."+fieldName;
	}

	@Override
	public void setHttpContext(HttpContext httpContext) {
		this.httpContext=httpContext;
	}
	
}
