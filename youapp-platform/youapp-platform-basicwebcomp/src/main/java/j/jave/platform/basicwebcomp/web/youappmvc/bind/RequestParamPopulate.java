package j.jave.platform.basicwebcomp.web.youappmvc.bind;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.random.JPopulate;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support.convert.JDataConvertor;
import j.jave.kernal.jave.support.detect.JDefaultFieldMeta;
import j.jave.kernal.jave.support.detect.JFieldDetector;
import j.jave.kernal.jave.support.detect.JFieldOnSingleClassDetector;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.web.youappmvc.model.HttpContext;

public class RequestParamPopulate implements JPopulate {

	private String prefix;
	
	private HttpContext httpContext;
	
	public RequestParamPopulate(String prefix,HttpContext httpContext){
		this.prefix=prefix;
		this.httpContext=httpContext;
	}
	
	@Override
	public void populate(final Object object) throws Exception {
		final JDataConvertor dataConvertor=JDataConvertor.build(JConfiguration.get());
		JFieldOnSingleClassDetector<JDefaultFieldMeta> fieldOnSingleClassDetector
		=new JFieldOnSingleClassDetector<JDefaultFieldMeta>(JFieldDetector.defaultFieldInfoGen);
		fieldOnSingleClassDetector.detect(object.getClass());
		JCollectionUtils.each(fieldOnSingleClassDetector.getFieldInfos(), new JCollectionUtils.CollectionCallback<JDefaultFieldMeta>() {
			@Override
			public void process(JDefaultFieldMeta fieldMeta) throws Exception {
				
				Field field=fieldMeta.getField();
				Class<?> type=field.getType();
				if(JClassUtils.isSimpleType(type)){
					Object value=null;
					if(JStringUtils.isNotNullOrEmpty(prefix)){
						value=httpContext.getParameterValue(getKey(fieldMeta.getFieldName()));
					}
					if(value==null){
						value=httpContext.getParameterValue(fieldMeta.getFieldName());
					}
					if(value!=null){
						field.set(object, dataConvertor.convert(type, value));
					}
				}
				else if(Map.class.isAssignableFrom(type)){
					
				}
				else if(Collection.class.isAssignableFrom(type)){
					
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
