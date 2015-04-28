package j.jave.framework.model.support.interceptor;

import j.jave.framework.model.support.JColumn;
import j.jave.framework.model.support.JFieldValidator;
import j.jave.framework.model.support.JSQLAnnotationConvert;
import j.jave.framework.model.support.JTYPE;
import j.jave.framework.model.support.JTable;
import j.jave.framework.reflect.JClassUtils;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author J
 *
 * @param <T>
 */
public class JModelValidatorIntercepter<T> implements JModelIntercepter<T> {

	private static final Logger LOGGER=LoggerFactory.getLogger(JModelValidatorIntercepter.class);
	
	/**
	 * the entity that maps to a concrete table, with annotaion 
	 * {@link JTable} 
	 */
	private final T object;
	
	public JModelValidatorIntercepter(T object) {
		this.object=object;
	}
	
	private StringBuffer invalidMessage=new StringBuffer();
	
	@Override
	public T intercept(JModelInvocation<T> modelInvocation) {
		Class<?> superClass=object.getClass();
		JTable table=superClass.getAnnotation(JTable.class);
		if(table==null){
			return object;
		}
		boolean valid=true;
		while(superClass!=null){
			Field[] fields=superClass.getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				Field field=fields[i];
				if(!JClassUtils.isAccessable(field)){
					field.setAccessible(true);
				}
				
				JColumn column=field.getAnnotation(JColumn.class);
				// check whether the property is mapping to a column of a table.
				if(column==null){
					continue;
				}
				else{
					JTYPE<?> type=JSQLAnnotationConvert.get(column);
					if(JFieldValidator.class.isInstance(type)){
						JFieldValidator validator= (JFieldValidator) type;
						Object obj=null;
						try {
							obj = field.get(object);
						} catch (Exception e) {
							LOGGER.info(e.getMessage(), e);
						} 
						if(!validator.validate(obj)){
							invalidMessage.append(type.name()+","+validator.invalidMessage());
							valid=false;
						}
					}
				}
			}
			superClass=superClass.getSuperclass();
		}
		
		if(!valid){
			throw new RuntimeException(invalidMessage.toString());
		}
		
		return object;
	}


}
